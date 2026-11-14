package com.example.theoven

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.theoven.databinding.ActivityPaymentBinding
import kotlinx.coroutines.launch
import java.util.UUID

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private var totalAmount: Int = 0
    private var deliveryFee: Int = 0
    private var orderType: String = "Delivery"
    private var locationAddress: String = ""

    private var selectedBankName: String? = null
    private var selectedScreenshotUri: String? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedScreenshotUri = uri.toString()
            binding.ivScreenshotPreview.setImageURI(uri)
            binding.ivScreenshotPreview.visibility = View.VISIBLE
            binding.layoutUploadPlaceholder.visibility = View.GONE
            binding.btnRemoveScreenshot.visibility = View.VISIBLE
            Toast.makeText(this, "Payment screenshot attached successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalAmount = intent.getIntExtra("EXTRA_TOTAL", 0)
        deliveryFee = intent.getIntExtra("EXTRA_DELIVERY_FEE", 100)
        orderType = intent.getStringExtra("EXTRA_ORDER_TYPE") ?: "Delivery"
        locationAddress = intent.getStringExtra("EXTRA_LOCATION") ?: ""

        setupToolbar()
        setupOrderDetails()
        setupCopyButton()
        setupBankDropdown()
        setupTogglePaymentMethod()
        setupPhotoPicker()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupOrderDetails() {
        binding.tvPaymentTotal.text = "Rs. $totalAmount"
        binding.tvOrderTypeBadge.text = orderType
        val savedPhone = UserManager.getPhone(this)
        binding.tvCODPhone.text = "Contact Phone: ${savedPhone.ifEmpty { "Not set - add it in your profile" }}"
    }

    private fun setupCopyButton() {
        binding.btnCopyAccount.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Meezan Bank Account", BankAppHelper.MERCHANT_ACCOUNT_NO)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Account Number (${BankAppHelper.MERCHANT_ACCOUNT_NO}) copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBankDropdown() {
        val bankList = BankAppHelper.getBankNamesList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bankList)
        binding.actvBank.setAdapter(adapter)

        binding.actvBank.setOnItemClickListener { _, _, position, _ ->
            val item = bankList[position]
            if (item.startsWith("---")) {
                binding.actvBank.setText("", false)
                selectedBankName = null
                binding.btnLaunchBankApp.text = "Open Selected Bank App 📱"
            } else {
                selectedBankName = item
                binding.btnLaunchBankApp.text = "Open $item App 📱"
            }
        }
    }

    private fun setupTogglePaymentMethod() {
        binding.togglePaymentMethod.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnBankTransfer -> {
                        binding.containerBankTransfer.visibility = View.VISIBLE
                        binding.containerCOD.visibility = View.GONE
                    }
                    R.id.btnCOD -> {
                        binding.containerBankTransfer.visibility = View.GONE
                        binding.containerCOD.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupPhotoPicker() {
        val clickListener = View.OnClickListener {
            pickImageLauncher.launch("image/*")
        }
        binding.cardUploadScreenshot.setOnClickListener(clickListener)
        binding.layoutUploadPlaceholder.setOnClickListener(clickListener)

        binding.btnRemoveScreenshot.setOnClickListener {
            selectedScreenshotUri = null
            binding.ivScreenshotPreview.visibility = View.GONE
            binding.layoutUploadPlaceholder.visibility = View.VISIBLE
            binding.btnRemoveScreenshot.visibility = View.GONE
        }
    }

    private fun setupButtons() {
        binding.btnLaunchBankApp.setOnClickListener {
            if (selectedBankName.isNull_or_empty()) {
                Toast.makeText(this, "Please select a bank or digital wallet first", Toast.LENGTH_SHORT).show()
            } else {
                BankAppHelper.launchBankApp(this, selectedBankName!!)
            }
        }

        binding.btnSubmitPayment.setOnClickListener {
            submitOrder()
        }
    }

    private fun String?.isNull_or_empty(): Boolean = this == null || this.trim().isEmpty()

    private fun submitOrder() {
        val isBankTransfer = binding.togglePaymentMethod.checkedButtonId == R.id.btnBankTransfer

        if (isBankTransfer) {
            if (selectedBankName.isNull_or_empty()) {
                Toast.makeText(this, "Please select your Bank or Digital Wallet App!", Toast.LENGTH_SHORT).show()
                return
            }
            if (selectedScreenshotUri.isNull_or_empty()) {
                Toast.makeText(this, "Please attach a screenshot proof of your payment!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Unique order ID (the old 4-digit random IDs could collide between customers).
        val orderId = UUID.randomUUID().toString().take(8).uppercase()
        val customerName = UserManager.getName(this)
        val customerPhone = UserManager.getPhone(this)

        val newOrder = OrderModel(
            orderId = orderId,
            customerName = customerName,
            customerPhone = customerPhone,
            deliveryAddress = if (locationAddress.isNotEmpty()) locationAddress else UserManager.getAddress(this),
            orderType = orderType,
            items = CartManager.getItems(this).toList(),
            totalAmount = totalAmount,
            deliveryFee = deliveryFee,
            paymentMethod = if (isBankTransfer) PaymentMethod.BANK_TRANSFER else PaymentMethod.CASH_ON_DELIVERY,
            selectedBankName = if (isBankTransfer) selectedBankName else null,
            screenshotUri = if (isBankTransfer) selectedScreenshotUri else null,
            status = if (isBankTransfer) PaymentStatus.PENDING_VERIFICATION else PaymentStatus.COD_CONFIRMED
        )

        // Local cache first — the order is never lost, even offline.
        OrderManager.addOrder(this, newOrder)

        // Best-effort cloud sync: the restaurant sees the order in the admin
        // panel when this succeeds. Failures never block the order flow.
        val signedIn = try {
            FirebaseHelper.isSignedIn()
        } catch (e: Exception) {
            false
        }
        if (signedIn) {
            binding.btnSubmitPayment.isEnabled = false
            lifecycleScope.launch {
                val result = FirestoreOrderRepository.createOrder(newOrder)
                binding.btnSubmitPayment.isEnabled = true
                if (result.isFailure) {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Order saved on this device — couldn't reach the server (${result.exceptionOrNull()?.message})",
                        Toast.LENGTH_LONG
                    ).show()
                }
                finishOrder(isBankTransfer, orderId)
            }
        } else {
            finishOrder(isBankTransfer, orderId)
        }
    }

    private fun finishOrder(isBankTransfer: Boolean, orderId: String) {
        NotificationHelper.showPaymentNotification(this, OrderManager.getOrderById(this, orderId)
            ?: return)

        CartManager.clearCart(this)

        val intent = Intent(this, OrderSuccessActivity::class.java).apply {
            putExtra("EXTRA_ORDER_ID", orderId)
            putExtra("EXTRA_IS_COD", !isBankTransfer)
        }
        startActivity(intent)
        finish()
    }
}

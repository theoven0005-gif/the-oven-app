package com.example.theoven

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

enum class BankCategory {
    COMMERCIAL_BANK,
    DIGITAL_WALLET
}

data class BankInfo(
    val name: String,
    val category: BankCategory,
    val packageName: String
)

object BankAppHelper {

    val MERCHANT_BANK_NAME = "MEEZAN BANK"
    val MERCHANT_ACCOUNT_TITLE = "The Oven Delco"
    val MERCHANT_ACCOUNT_NO = "35010115030079"

    val supportedBanks = listOf(
        // Digital Wallets & Fintech
        BankInfo("Easypaisa", BankCategory.DIGITAL_WALLET, "com.easypaisa.android"),
        BankInfo("JazzCash", BankCategory.DIGITAL_WALLET, "com.techlogix.mobilinkcustomer"),
        BankInfo("NayaPay", BankCategory.DIGITAL_WALLET, "com.nayapay.app"),
        BankInfo("SadaPay", BankCategory.DIGITAL_WALLET, "pk.sadapay.app"),

        // Commercial & Islamic Banks
        BankInfo("Meezan Bank", BankCategory.COMMERCIAL_BANK, "com.meezanbank.mb"),
        BankInfo("HBL (Habib Bank Limited)", BankCategory.COMMERCIAL_BANK, "com.hbl.mobilebanking"),
        BankInfo("UBL (United Bank Limited)", BankCategory.COMMERCIAL_BANK, "com.ubl.digital"),
        BankInfo("MCB Bank", BankCategory.COMMERCIAL_BANK, "com.mcb.mcbmobile"),
        BankInfo("Bank Alfalah", BankCategory.COMMERCIAL_BANK, "com.bankalfalah.albi"),
        BankInfo("Allied Bank (ABL)", BankCategory.COMMERCIAL_BANK, "com.abl.myabl"),
        BankInfo("Askari Bank", BankCategory.COMMERCIAL_BANK, "com.askari.mobile"),
        BankInfo("Faysal Bank", BankCategory.COMMERCIAL_BANK, "com.faysal.digi"),
        BankInfo("Bank of Punjab (BOP)", BankCategory.COMMERCIAL_BANK, "com.bop.mobile"),
        BankInfo("National Bank of Pakistan (NBP)", BankCategory.COMMERCIAL_BANK, "pk.com.nbp.digital"),
        BankInfo("JS Bank", BankCategory.COMMERCIAL_BANK, "com.js.wallet"),
        BankInfo("Standard Chartered Pakistan", BankCategory.COMMERCIAL_BANK, "com.sc.scmobile.pk"),
        BankInfo("Habib Metropolitan Bank", BankCategory.COMMERCIAL_BANK, "com.netsol.alhabib"),
        BankInfo("Soneri Bank", BankCategory.COMMERCIAL_BANK, "com.soneribank.digital")
    )

    fun getBankNamesList(): List<String> {
        val result = mutableListOf<String>()
        result.add("--- 📱 Digital Wallets & Fintech ---")
        result.addAll(supportedBanks.filter { it.category == BankCategory.DIGITAL_WALLET }.map { it.name })
        result.add("--- 🏛️ Commercial & Islamic Banks ---")
        result.addAll(supportedBanks.filter { it.category == BankCategory.COMMERCIAL_BANK }.map { it.name })
        return result
    }

    fun findBankByName(name: String): BankInfo? {
        return supportedBanks.find { it.name.equals(name, ignoreCase = true) }
    }

    fun launchBankApp(context: Context, bankName: String) {
        val bank = findBankByName(bankName)
        if (bank == null) {
            Toast.makeText(context, "Please select a valid bank or digital wallet", Toast.LENGTH_SHORT).show()
            return
        }

        val pm = context.packageManager
        val launchIntent = pm.getLaunchIntentForPackage(bank.packageName)

        if (launchIntent != null) {
            try {
                context.startActivity(launchIntent)
                Toast.makeText(context, "Opening ${bank.name}...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                openPlayStore(context, bank)
            }
        } else {
            openPlayStore(context, bank)
        }
    }

    private fun openPlayStore(context: Context, bank: BankInfo) {
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${bank.packageName}"))
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(marketIntent)
            Toast.makeText(context, "${bank.name} app is not installed. Opening Play Store...", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${bank.packageName}"))
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(webIntent)
            Toast.makeText(context, "Opening Google Play Store for ${bank.name}...", Toast.LENGTH_LONG).show()
        }
    }
}

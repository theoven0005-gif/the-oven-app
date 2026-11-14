# Profile Activity Enhancement Plan

This plan aims to make the Profile Activity fully functional by adding user data persistence, editing capabilities, and navigation to related sections.

## User Review Required

> [!IMPORTANT]
> The current user data is hardcoded. I will introduce a `UserManager` using `SharedPreferences` for persistence.
> "My Orders" and "Saved Addresses" will be implemented as new activities.

## Proposed Changes

### [Core Data]

#### [NEW] [UserManager.kt](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/java/com/example/theoven/UserManager.kt)
A singleton to manage user profile data (Name, Phone, Email, Address) using SharedPreferences.

### [UI Enhancement]

#### [MODIFY] [activity_profile.xml](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/res/layout/activity_profile.xml)
- Add IDs to "My Orders" and "Saved Addresses" buttons.
- Add an "Edit" icon/button to the profile section.
- Add IDs to the Name and Phone text views to update them dynamically.

#### [MODIFY] [ProfileActivity.kt](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/java/com/example/theoven/ProfileActivity.kt)
- Bind user data from `UserManager`.
- Set up click listeners for all buttons.

#### [NEW] [EditProfileActivity.kt](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/java/com/example/theoven/EditProfileActivity.kt)
#### [NEW] [activity_edit_profile.xml](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/res/layout/activity_edit_profile.xml)
Activity to allow users to update their personal information.

#### [NEW] [OrdersActivity.kt](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/java/com/example/theoven/OrdersActivity.kt)
#### [NEW] [activity_orders.xml](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/res/layout/activity_orders.xml)
Placeholder/Basic activity to show user orders.

#### [NEW] [AddressesActivity.kt](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/java/com/example/theoven/AddressesActivity.kt)
#### [NEW] [activity_addresses.xml](file:///C:/Users/Itcomplex/AndroidStudioProjects/TheOven/app/src/main/res/layout/activity_addresses.xml)
Placeholder/Basic activity to manage saved addresses.

## Verification Plan

### Automated Tests
- N/A (Focusing on UI and basic logic)

### Manual Verification
- Deploy to device/emulator.
- Navigate to Profile.
- Edit profile and verify changes persist after app restart.
- Verify logout functionality.
- Verify navigation to My Orders and Saved Addresses.

# Bayt Al Hekma Library Management System

## 📚 Project Overview

**Bayt Al Hekma Library Management System** is a Java console application designed to manage a small community library.

The system manages:

* Library books, magazines, and DVDs.
* Members and their borrowing information.
* Item availability and loan status.
* Borrowing and returning operations.
* Loan renewals.
* Late-return fines.
* Membership-based fine waivers.
* Administrative charges.
* Outstanding member balances.
* Library statistics and reports.

The project demonstrates important **Object-Oriented Programming (OOP)** concepts such as:

* Abstraction
* Inheritance
* Polymorphism
* Encapsulation
* Interfaces
* Enumerations
* Association between classes
* Collections using arrays

---

## 🎯 Main Objectives

The system is designed to:

1. Manage different types of library items using a common parent class.
2. Manage library members and their borrowing limits.
3. Prevent invalid borrowing and returning operations.
4. Calculate fines according to the item type.
5. Apply membership-specific waivers.
6. Track outstanding balances.
7. Support renewable and non-renewable items.
8. Generate useful library reports.

---

# 🏗️ System Structure

The main classes and types are:

```text
                    LibraryItem
                   <<abstract>>
                        |
          +-------------+-------------+
          |             |             |
        Book         Magazine         DVD
          |
          +------ implements ------+
                                  |
                              Renewable
```

Other important components:

```text
Library
 ├── LibraryItem[]
 └── Member[]

Member
 └── MembershipType

LibraryItem
 └── ItemStatus
```

---

# 🧩 Main Components

## 1. ItemStatus

`ItemStatus` is an enumeration representing the current state of a library item.

It contains:

* `AVAILABLE` — the item can be borrowed.
* `ON_LOAN` — the item is currently borrowed.
* `LOST` — the item has been lost and cannot be borrowed.

An item must always have exactly one status.

---

## 2. MembershipType

`MembershipType` is an enumeration representing the different membership categories.

Each membership type stores its own **fine waiver rate**.

This design means that the waiver percentage is stored directly inside the enum instead of requiring another table or collection.

Adding a new membership type only requires adding another enum value with its corresponding waiver rate.

---

## 3. Renewable

`Renewable` is an interface that represents the ability of an item to renew its loan.

It provides two operations:

```text
Renew the loan
Report the maximum renewal limit
```

The following items implement `Renewable`:

* Book
* Magazine

DVD does not implement it because DVDs cannot be renewed.

This demonstrates the idea of an **interface representing a capability**.

---

# 👤 4. Member

The `Member` class represents a library member.

### Stored Information

Each member contains:

* Name
* Membership ID
* Membership type
* Balance owed
* Number of items currently held

### Main Responsibilities

A member can:

* Display their information.
* Change their name.
* Receive a fine.
* Make a payment.
* Check borrowing eligibility.
* Record a borrowing.
* Record a return.

### Borrowing Rules

A member can borrow an item only when:

```text
Items currently held < 3
AND
Balance owed <= 100 EGP
```

### Balance Rules

* A fine must be positive.
* A payment must be positive.
* A payment cannot exceed the current balance.
* The balance can never become negative.

### Item Count Rules

* Borrowing increases the count by `1`.
* Returning decreases the count by `1`.
* The count can never become negative.

The class uses **encapsulation** to prevent other classes from directly changing the balance or number of borrowed items.

---

# 📦 5. LibraryItem

`LibraryItem` is an **abstract class** and the parent class of:

* `Book`
* `Magazine`
* `DVD`

It stores the information common to all library items.

### Common Fields

* Catalogue ID
* Title
* Current status
* Borrower membership ID
* Number of renewals used

It also stores library-wide information such as:

* Library name
* Administrative charge
* Total number of items ever added

### Main Responsibilities

`LibraryItem` can:

* Display its information.
* Be marked as lost.
* Be returned.
* Be lent to a member.
* Record a renewal.

It cannot be instantiated directly because it is abstract.

---

# 🔄 Polymorphism

Every child class provides its own implementation for:

1. Fine calculation.
2. Loan period.
3. Item category name.

For example:

```text
Book
  Loan period = 14 days
  Fine = 5 EGP/day

Magazine
  Loan period = 7 days
  Fine = 3 EGP/day
  Maximum fine = 30 EGP

DVD
  Loan period = 3 days
  Fine = 15 EGP/day
```

The `Library` can therefore work with:

```java
LibraryItem
```

without needing to know whether the actual object is a `Book`, `Magazine`, or `DVD`.

This is an example of **polymorphism**.

---

# 📚 6. Item Types

## Book

A `Book` contains:

* Author
* Page count

Rules:

| Property      |     Value |
| ------------- | --------: |
| Loan period   |   14 days |
| Fine          | 5 EGP/day |
| Renewal limit |   2 times |

Books implement `Renewable`.

---

## Magazine

A `Magazine` contains:

* Issue number

Rules:

| Property      |     Value |
| ------------- | --------: |
| Loan period   |    7 days |
| Fine          | 3 EGP/day |
| Maximum fine  |    30 EGP |
| Renewal limit |    1 time |

Magazines implement `Renewable`.

The magazine fine is capped at **30 EGP**.

---

## DVD

A `DVD` contains:

* Runtime

Rules:

| Property    |       Value |
| ----------- | ----------: |
| Loan period |      3 days |
| Fine        |  15 EGP/day |
| Renewal     | Not allowed |

DVD does not implement `Renewable`.

---

# 🏛️ 7. Library

The `Library` class is responsible for managing the entire library.

It contains two main collections:

```text
LibraryItem[] catalogue
Member[] members
```

Each collection also tracks how many positions are currently being used.

### Registration

The library rejects:

* Items when the catalogue is full.
* Items with duplicate catalogue IDs.
* Members when the member register is full.
* Members with duplicate membership IDs.

---

# 🔎 Searching

The library can search for:

### Item

Search using:

```text
Catalogue ID
```

### Member

Search using:

```text
Membership ID
```

If an item or member does not exist, the search returns nothing and the calling operation handles the error.

---

# 📖 Borrowing

To borrow an item, the system:

1. Finds the item.
2. Finds the member.
3. Checks that both exist.
4. Checks that the item is `AVAILABLE`.
5. Checks that the member is eligible.
6. Stores the member's ID as the borrower.
7. Changes the item status to `ON_LOAN`.
8. Increases the member's held-item count.

---

# ↩️ Returning

When returning an item:

1. Find the item by catalogue ID.
2. Verify that it is currently `ON_LOAN`.
3. Reject negative overdue days.
4. Read the borrower membership ID.
5. Find the corresponding member.
6. Calculate the fine.
7. Apply the membership waiver to the base fine.
8. Add the administrative charge.
9. Add the final amount to the member's balance.
10. Decrease the member's held-item count.
11. Reset the item.

Returning an item resets:

```text
Status → AVAILABLE
Borrower ID → cleared
Renewal count → 0
```

The borrower must be found **before** the item is reset because returning the item clears its borrower ID.

---

# 💰 Fine Calculation

The fine depends on the item type and number of overdue days.

### Important Rules

* `0` overdue days → no fine.
* Negative overdue days → rejected.
* Book → 5 EGP/day.
* Magazine → 3 EGP/day, maximum 30 EGP.
* DVD → 15 EGP/day.

The membership waiver applies only to the **base fine**.

The administrative charge cannot be waived.

Conceptually:

```text
Base Fine
      ↓
Membership Waiver
      ↓
Reduced Fine
      +
Administrative Charge
      ↓
Final Amount
```

---

# 🔁 Renewing

Only renewable items can be renewed.

### Books

Maximum:

```text
2 renewals
```

### Magazines

Maximum:

```text
1 renewal
```

### DVDs

Renewal is not allowed.

A renewal is successful only when:

```text
Item supports Renewable
AND
Item is ON_LOAN
AND
Renewal limit has not been reached
```

If an item cannot be renewed, the program displays a clear message instead of crashing.

---

# ❌ Marking an Item as Lost

An item can be marked as `LOST` only when its current status is:

```text
AVAILABLE
```

If the item is:

```text
ON_LOAN
```

the operation is rejected.

A lost item cannot be borrowed.

---

# 📊 Library Reports

The system provides a library report containing:

* Catalogue size
* Number of items ever added
* Number of items currently on loan
* Loan rate
* Total outstanding balances
* Projected fines

The projected fine calculation assumes that every item currently on loan is returned **5 days late**.

### Loan Rate

The loan rate represents the percentage of catalogue items currently on loan:

```text
Loan Rate =
Items On Loan / Catalogue Size × 100
```

---

# 🖥️ Program Operations

The program provides the following menu:

```text
1. View Catalogue
2. Register Member
3. Borrow Item
4. Return Item
5. Renew Loan
6. Search Item by ID
7. View Items by Status
8. Pay Outstanding Fines
9. View All Members
10. Library Report
11. Mark Item as Lost
0. Exit
```

After every operation, the program returns to the main menu.

---

# 📋 Operation Details

| Option | Operation       | Description                                |
| ------ | --------------- | ------------------------------------------ |
| 1      | View Catalogue  | Display all library items                  |
| 2      | Register Member | Add a new member                           |
| 3      | Borrow Item     | Borrow an available item                   |
| 4      | Return Item     | Return an item and calculate the fine      |
| 5      | Renew Loan      | Attempt to renew an eligible item          |
| 6      | Search Item     | Find an item by ID                         |
| 7      | View by Status  | Filter items by status                     |
| 8      | Pay Fines       | Pay part or all of the outstanding balance |
| 9      | View Members    | Display all members                        |
| 10     | Library Report  | Display library statistics                 |
| 11     | Mark as Lost    | Mark an available item as lost             |
| 0      | Exit            | Close the program                          |

---

# 🧠 OOP Concepts Demonstrated

## Encapsulation

Classes control access to their internal data.

For example, `Member` does not allow another class to directly set:

```text
balance
number of held items
```

Instead, controlled methods are used.

---

## Abstraction

`LibraryItem` is abstract because there is no generic library item that should be created directly.

Instead, the system creates:

```text
Book
Magazine
DVD
```

---

## Inheritance

The three item types inherit common properties and behavior from `LibraryItem`.

```text
Book extends LibraryItem
Magazine extends LibraryItem
DVD extends LibraryItem
```

This avoids duplicating common code.

---

## Polymorphism

The library stores all item types in one collection:

```java
LibraryItem[]
```

For example:

```text
Book
Magazine
DVD
```

can all exist inside the same catalogue.

The library can call common methods without knowing the exact child type.

---

## Interface

`Renewable` represents the capability of renewing a loan.

Books and magazines implement it, while DVDs do not.

This prevents forcing every library item to support renewal.

---

## Enumeration

Enums are used for fixed sets of values:

```text
ItemStatus
MembershipType
```

This makes the code safer and clearer than using arbitrary strings.

---

# 🔐 Rules Enforced by the System

### Catalogue

* Catalogue IDs must be unique.
* Item IDs cannot change after creation.
* Titles cannot change after creation.
* Only `AVAILABLE` items can be borrowed.
* `LOST` items cannot be borrowed.
* Only `AVAILABLE` items can be marked as lost.
* Only `ON_LOAN` items can be returned.

### Members

* Membership IDs must be unique.
* Membership ID cannot change.
* Membership category cannot change.
* Maximum 3 borrowed items.
* Members owing more than 100 EGP cannot borrow.
* Fines must be positive.
* Payments must be positive.
* Payments cannot exceed the outstanding balance.
* Balance cannot become negative.
* Held-item count cannot become negative.

### Fines

* Overdue days cannot be negative.
* No overdue days means no fine and no administrative charge.
* Magazine fines cannot exceed 30 EGP.
* Membership waiver applies only to the base fine.
* Administrative charge cannot be waived.
* Borrower must be identified before the item is returned.

### Renewals

* Only renewable items can be renewed.
* Items must currently be on loan.
* Renewal limits cannot be exceeded.
* Non-renewable items must produce an explanation instead of an error/crash.

---

# 🏁 Main Program Flow

The `Main` class is responsible for interacting with the user.

The general flow is:

```text
Start Program
     ↓
Create Library
     ↓
Add Sample Items
     ↓
Add Sample Members
     ↓
Display Menu
     ↓
Read User Choice
     ↓
Perform Operation
     ↓
Return to Menu
     ↓
Repeat
     ↓
Exit
```

The `Main` class handles user input and menu navigation, while the other classes are responsible for their own data and business rules.

This separation makes the program easier to understand, test, and maintain.

---

# 📌 Design Philosophy

The project is organized so that **each class has a clear responsibility**.

* `Member` manages member information and borrowing eligibility.
* `LibraryItem` manages common item behavior.
* `Book`, `Magazine`, and `DVD` provide item-specific rules.
* `Renewable` represents the ability to renew.
* `Library` manages the catalogue, members, and library operations.
* `Main` handles user interaction.

This organization reduces duplicated code, improves maintainability, and demonstrates good Object-Oriented Programming practices.

---

# 🚀 Conclusion

The **Bayt Al Hekma Library Management System** is a complete console-based library management application that combines practical library functionality with core Java OOP principles.

The system provides controlled borrowing, returning, renewal, fine calculation, payment, lost-item management, member management, and reporting while enforcing all required business rules.

The use of **abstraction, inheritance, polymorphism, encapsulation, interfaces, and enums** makes the design organized, reusable, and easy to extend with additional library item types or membership categories in the future.

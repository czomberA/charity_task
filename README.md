# charity_task
## REST API endpoints
1. Create a new fundraising event.</br>
   POST http://localhost:8080/api/fundraiser</br>
   sample query:
   `{
      "name": "test",
      "currency": "EUR"
      }`
   </br>Name is a unique String (at least 1 character, maximum 255 characters).
   </br>Currency is a 3 letter code (ISO 4217). Only 3 currencies are supported - **PLN**, **GBP** and **EUR**.
2. Register a new collection box.</br>
   POST http://localhost:8080/api/box</br>
   sample query:
   `{
       "identifier": "test"
   }`
   </br>Identifier is a unique String (at least 1 character, maximum 255 characters).
3. List all collection boxes. Include information if the box is assigned (but don’t expose to what
   fundraising event) and if it is empty or not (but don’t expose the actual value in the box).</br>
   GET http://localhost:8080/api/box</br>
4. Unregister (remove) a collection box (e.g. in case it was damaged or stolen).</br>
   DELETE http://localhost:8080/api/box/{box_identifier}</br>
5. Assign the collection box to an existing fundraising event.</br>
   PUT http://localhost:8080/api/box/{box_identifier}</br>
   sample query:
   `{
       "fundraiser": "test"
   }`
6. Put (add) some money inside the collection box.</br>
   PUT http://localhost:8080/api/box/donate</br>
   sample query:
   `{
       "charityBox" : "test",
       "amount": 5.99,
       "currency": "EUR"
   }`
   </br>Amount cannot have more than 2 decimal places. Maximum value is 9,999,999,999.99  (10 digits before the decimal, 2 after). Please note, that it's also a maximum value a Fundraiser and a Collected table can store. Donating maximum value multiple times or in addition to other donations will cause errors.
   </br>Currency is a 3 letter code (ISO 4217). Only 3 currencies are supported - **PLN**, **GBP** and **EUR**.
7. Empty the collection box i.e. “transfer” money from the box to the fundraising event’s account.</br>
   PUT http://localhost:8080/api/box/empty/{box_identifier}</br>
8. Display a financial report with all fundraising events and the sum of their accounts.</br>
   GET http://localhost:8080/api/fundraiser
## Pre-created objects
There are 3 pre-created Fundraisers:
| Fundraiser  | Amount | Currency
| ------------- | ------------- | ------------- |
| ALL FOR HOPE  | 100.00  | EUR |
| HELP THE WHALES  | 0.00  | PLN |
| CHILDREN IN NEED | 0.00 | GBP |

And 4 pre-created charity boxes:
| Identifier  | Fundraiser |
| ------------- | ------------- |
| A01  | ALL FOR HOPE  |
| A02  | ALL FOR HOPE  |
| A03 | HELP THE WHALES |
| A04 | _unassigned_ |

All charity boxes are empty.
## About currency exchange
Unfortunately, I wasn't able to find a free and unlimited currency exchange API that supports direct conversion between arbitrary currency pairs. Because of this limitation, I implemented a workaround, that slightly complicates the exchangle logic.</br> I am using the [NBP Web API](https://api.nbp.pl/), which only provides currency rates ralative to PLN. As a result, when converting between two non-PLN currencies (e.g., from EUR to GBP), the amount is first converted from EUR to PLN, and then from PLN to GBP.</br>
This indirect conversion method is not ideal for production use, as it may lead to small discrepancies due to double conversion. However, I believe it's acceptable for the purpose of this task.
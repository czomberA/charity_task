# charity_task
## REST API endpoints
1. Create a new fundraising event.</br>
POST http://localhost:8080/api/fundraiser</br>
example code: 
'''{
   "name": "ToniOsteoporosis",
   "currency": "EUR"
   }'''
2. Register a new collection box.
3. List all collection boxes. Include information if the box is assigned (but don’t expose to what
   fundraising event) and if it is empty or not (but don’t expose the actual value in the box).
4. Unregister (remove) a collection box (e.g. in case it was damaged or stolen).
5. Assign the collection box to an existing fundraising event.
6. Put (add) some money inside the collection box.
7. Empty the collection box i.e. “transfer” money from the box to the fundraising event’s account.
8. Display a financial report with all fundraising events and the sum of their accounts.
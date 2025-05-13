INSERT INTO FUNDRAISER (NAME, CURRENCY, ACCOUNT) VALUES ('ALL FOR HOPE', 'EUR', 100.00),
                                               ('HELP THE WHALES', 'PLN', 0),
                                               ('CHILDREN IN NEED', 'GBP', 0);

INSERT INTO CHARITY_BOX (FUNDRAISER_ID, IDENTIFIER) VALUES (1, 'A01'),
                                            (1, 'A02'),
                                            (2, 'A03'),
                                            (NULL, 'A04');

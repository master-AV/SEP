insert into webshop(merchant_id, merchant_password) values ('1', '123');

insert into account_information(pan, security_code, card_holder_name, expiration_date, user_id)
values  ('0321', '123', 'Petar Petrovic', '2024-03-31', 1);
insert into account_information(pan, security_code, card_holder_name, expiration_date, user_id)
values  ('1321', '123', 'Milos Ceca', '2024-03-31', 10);
insert into payment_method(name, img, subscribed) values ('CREDIT CARD', 'credit-card.png', true),
                                                         ('QR CODE', 'qr-code.png', false),
                                                         ('PAYPAL', 'paypal.png', true),
                                                         ('BITCOIN', 'bitcoin.png', false);

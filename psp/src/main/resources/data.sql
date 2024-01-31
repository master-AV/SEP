insert into webshop(merchant_id, merchant_password) values ('1', '123');
insert into webshop(merchant_id, merchant_password) values ('0.0.7131839', '3030020100300706052b8104000a04220420200d582de19ffff83de48eefaf5fefc24a022f5c6521bd7ba2c0023d4d46f806');


insert into account_information(pan, security_code, card_holder_name, expiration_date, user_id)
values  ('0321', '123', 'Petar Petrovic', '2024-03-31', 1);
insert into account_information(pan, security_code, card_holder_name, expiration_date, user_id)
values  ('1321', '123', 'Milos Ceca', '2024-03-31', 10);
insert into payment_method(name, img, subscribed) values ('CREDIT CARD', 'credit-card.png', true),
                                                         ('QR CODE', 'qr-code.png', false),
                                                         ('PAYPAL', 'paypal.png', true),
                                                         ('BITCOIN', 'bitcoin.png', false);

insert into wallet_information(account_id, account_key, user_id)
values  ('0.0.7714245', '3030020100300706052b8104000a04220420c552e696c9b4fb9ed95d9800ce6703769cf7be60d74074a3fa5f06aeb40ca72e', 1);

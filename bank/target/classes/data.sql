insert into account(pan, security_code, card_holder_name, expiration_date, reserved_money, available_money) values ('0123', '123', 'OTP', '2028-03-31', 0, 100000);
insert into account(pan, security_code, card_holder_name, expiration_date, reserved_money, available_money) values ('0542', '123', 'Reifeisen', '2029-03-31', 0, 10000);
insert into account(pan, security_code, card_holder_name, expiration_date, reserved_money, available_money) values ('0321', '123', 'Petar Petrovic', '2024-03-31', 100, 5000);
insert into account(pan, security_code, card_holder_name, expiration_date, reserved_money, available_money) values ('0322', '123', 'Petar Petrovic', '2024-03-31', 100, 5000);
insert into sales_account(merchant_id, merchant_password, account_id) values ('1', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1);
-- insert into sales_account(merchant_id, merchant_password, account_id) values ('2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1);
insert into payment_information(payment_id, account_id, amount, merchant_order_id) values ('1', 1, 20, 1);


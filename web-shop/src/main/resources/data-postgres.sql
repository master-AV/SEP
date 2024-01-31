
insert into offer (offer_type, price) values
                                                                (0, 200),
                                                                (1, 300),
                                                                (2, 400),
                                                                (3, 500),
                                                                (4, 600);

insert into role(role_name) values ('ROLE_USER');  -- 1
insert into role(role_name) values ('ROLE_ADMIN');   -- 2

insert into users (name, surname, email, verified, password, role_id, expires_membership, yearly_subscription) values
    ('Ana', 'Anic', 'ana@gmail.com', true, '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 1, null, false);
insert into users (name, surname, email, verified, password, role_id, expires_membership, yearly_subscription) values
    ('Ana', 'Anic', 'ana+123@gmail.com', true, '$2y$10$uwgoYpON2hx80Xpfgn4.O.j0Pys.uATCE2gQu3BNr/DwC8qn6G9am', 2, null, false);

insert into transactions (paid_date, payment_method, user_id, offer_id) values
                                                                        (to_timestamp('2024-01-20 15:10:00', 'YYYY-MM-DD HH24:MI:SS'), 'PAYPAL', 1, 1);

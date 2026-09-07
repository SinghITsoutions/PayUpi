INSERT IGNORE INTO banks (bank_name, bank_code, ifsc_prefix) VALUES
('State Bank of India', 'SBI', 'SBIN0001001'),
('HDFC Bank', 'HDFC', 'HDFC0002001'),
('ICICI Bank', 'ICICI', 'ICIC0003001'),
('Axis Bank', 'AXIS', 'UTIB0004002'),
('Punjab National Bank', 'PNB', 'PUNB556464'),
('Bank of Baroda', 'BOB', 'BARB388999'),
('Canara Bank', 'CANARA', 'CNRB5797979'),
('Union Bank of India', 'UBI', 'UBIN9098'),
('AU Small Finance Bank','AUSF','AUB4572'),
('Airtel Payments Bank','APB','APB98980'),
('Central Bank Of India','CBOI','CBI23710'),
('India Post Payment Bank','IPPB','IPB57890'),
('Kotak Mahindra Bank','KMB','KMB90820'),
('Paytm Payments Bank','PPB','PPB9893450'),
('Yes Bank','YB','YB128750'),
('Bank Of Baroda','BOB','BOB459820'),
('Bandhan Bank','BB','BB36090');






-- ==========================
-- BANK ACCOUNTS
-- ==========================

INSERT INTO bank_accounts
(account_number, account_holder_name, ifsc_code, balance,mobile_number,bank_id)
VALUES

-- Vishvjeet (SBI & HDFC)
('100100100001', 'Vishvjeet', 'SBIN0001001', 250000.00,7974170858,1),
('200200200001', 'Vishvjeet', 'UTIB0004002', 185000.00,7974170858,4),

-- Uma Panwar (ICICI & Axis)
('300300300001', 'Uma Panwar', 'HDFC0002001', 98000.00,9244170200,2),
('400400400001', 'Uma Panwar', 'ICIC0003001', 125000.00,9244170200,3),

-- Shivpratap (SBI & ICICI)
('100100100002', 'Shivpratap', 'BARB388999', 450000.00,6268194236,6),
('300300300002', 'Shivpratap', 'CNRB5797979', 76000.00,6268194236,7),

-- Raj Rawal (HDFC & Axis)
('200200200002', 'Raj Rawal', 'PUNB556464', 315000.00,9753002584,5),
('400400400002', 'Raj Rawal', 'UBIN9098', 210000.00,9753002584,8);
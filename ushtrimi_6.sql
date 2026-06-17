CREATE TABLE products(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(1000),
    cost DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    commission DOUBLE
);

CREATE  TABLE size(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20),
    surcharge DOUBLE DEFAULT 0
)

CREATE TABLE Inventory(
    barcode INT AUTO_INCREMENT PRIMARY KEY,
    state INT NOT NULL,
    product_id INT,
    size_id INT,
    sold_date DATETIME,
    sold_price DOUBLE
)

INSERT INTO inventory
VALUES
(1001,1,1,1,NULL,NULL),
(1002,1,1,4,NULL,NULL),
(1003,1,2,2,NULL,NULL),
(1004,1,3,4,NULL,NULL);

SELECT *
FROM inventory i
JOIN product p
ON i.product_id = p.id;

--1
SELECT 
    p.name,
    s.name AS size,
    p.price + s.surcharge AS total_price
    JOIN inventory i 
    ON p.id = i.product_id
    JOIN size s
    ON i.size_id = s.id 
    ORDER BY total_price



--2
SELECT
    p.name,

    CASE
        WHEN s.name = 's' THEN 'small'
        WHEN s.name = 'm' THEN 'medium'
        WHEN s.name = 'l' THEN 'large'
        WHEN s.name = 'xl' THEN 'extra large'
        ELSE s.name
    END AS size_name

FROM inventory i
JOIN product p
    ON i.product_id = p.id
JOIN size s
    ON i.size_id = s.id;

--3
SELECT
p.name,
COUNT(*) AS total
FROM inventory i
JOIN product p
    ON i.product_id = p.id
GROUP BY p.name;

--4
SELECT
p.name,
s.name,
COUNT(*) AS total
FROM inventory i
JOIN product p
    ON i.product_id = p.id
JOIN size s
    ON i.size_id = s.id
GROUP BY p.name, s.name;

--5
SELECT
p.name,
s.name,
COUNT(*) AS total
FROM inventory i
JOIN product p
    ON i.product_id = p.id
JOIN size s
    ON i.size_id = s.id
WHERE s.name <> 'xl'
GROUP BY p.name, s.name;

--6
SELECT
p.name,
s.name,
COUNT(*) AS total
FROM inventory i
JOIN product p
    ON i.product_id = p.id
JOIN size s
    ON i.size_id = s.id
WHERE p.name <> "HAT"
GROUP BY p.name,s.name;

--7

SELECT 
p.name,
s.name,
COUNT(*) AS total
FROM inventory i
JOIN product p
    ON i.product_id = p.id
JOIN size s
    ON s.id = i.size_id
GROUP BY p.name, s.name
HAVING total <= 25


--8
CREATE TABLE jewlery(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(1000),
    price DOUBLE
);

--9
SELECT
    id,
    name,
    description,
    price
FROM product

UNION

SELECT
    id,
    name,
    description,
    price
FROM jewelry;

--10
CREATE TABLE transaction(
    id INT AUTO_INCREMENT PRIMARY KEY
    date DATETIME
    barcode INT,
    sold_price DOUBLE,

    FOREIGN KEY(barcode)
        REFERENCES inventory(barcode)
);

DELIMITER //

CREATE PROCEDURE sell_product(IN p_barcode INT)
BEGIN
    DECLARE v_state INT;
    DECLARE v_price DOUBLE;
    DECLARE v_exists INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Check if the barcode exists
    SELECT COUNT(*)
    INTO v_exists
    FROM inventory
    WHERE barcode = p_barcode;

    IF v_exists = 0 THEN
        ROLLBACK;

    ELSE

        -- Get current state and total price
        SELECT i.state,
               p.price + s.surcharge
        INTO v_state, v_price
        FROM inventory i
        JOIN product p
            ON i.product_id = p.id
        JOIN size s
            ON i.size_id = s.id
        WHERE i.barcode = p_barcode;

        -- Product can be sold only if available or returned
        IF v_state = 1 OR v_state = 4 THEN

            INSERT INTO transactions
            (
                transaction_date,
                barcode,
                sold_price
            )
            VALUES
            (
                NOW(),
                p_barcode,
                v_price
            );

            UPDATE inventory
            SET state = 3,
                sold_date = NOW(),
                sold_price = v_price
            WHERE barcode = p_barcode;

            COMMIT;

        ELSE
            ROLLBACK;
        END IF;

    END IF;

END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE calculate_monthly_profit()
BEGIN
    DECLARE total_profit DOUBLE DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;

    SELECT IFNULL(SUM(t.sold_price - p.cost), 0)
    INTO total_profit
    FROM transactions t
    JOIN inventory i
        ON t.barcode = i.barcode
    JOIN product p
        ON i.product_id = p.id
    WHERE t.transaction_date >= DATE_SUB(NOW(), INTERVAL 1 MONTH);

    COMMIT;

    SELECT total_profit AS monthly_profit;

END //

DELIMITER ;


ALTER TABLE orders ALTER COLUMN id TYPE INT;

ALTER TABLE orders ALTER COLUMN id SET NOT NULL;

ALTER TABLE orders ADD PRIMARY KEY (id, product_id);

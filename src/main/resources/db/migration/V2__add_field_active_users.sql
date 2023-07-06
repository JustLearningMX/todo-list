ALTER table users ADD COLUMN active boolean NOT NULL DEFAULT true;
ALTER table users MODIFY COLUMN email varchar(255) NOT NULL unique;
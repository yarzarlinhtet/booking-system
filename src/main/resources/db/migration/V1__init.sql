CREATE TABLE IF NOT EXISTS class_booking (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   user_id UUID,
   classes_id UUID,
   booking_status VARCHAR(255),
   booked_at TIMESTAMP WITH TIME ZONE,
   refund_at TIMESTAMP WITH TIME ZONE,
   CONSTRAINT pk_class_booking PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS class_credit_transaction (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   user_id UUID,
   classes_id UUID,
   credit_amount INTEGER NOT NULL,
   credit_used BOOLEAN DEFAULT FALSE,
   refunded BOOLEAN DEFAULT FALSE,
   CONSTRAINT pk_class_credit_transaction PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS classes (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   country_code VARCHAR(50) NOT NULL,
   name VARCHAR(255) NOT NULL,
   description TEXT NOT NULL,
   start_at TIMESTAMP WITH TIME ZONE NOT NULL,
   end_at TIMESTAMP WITH TIME ZONE NOT NULL,
   max_capacity INTEGER NOT NULL,
   credit_amount INTEGER NOT NULL,
   CONSTRAINT pk_classes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS class_packages (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   country_code VARCHAR(50) NOT NULL,
   name VARCHAR(255) NOT NULL,
   description TEXT NOT NULL,
   credit_amount INTEGER NOT NULL,
   price DECIMAL NOT NULL,
   is_active BOOLEAN DEFAULT TRUE NOT NULL,
   CONSTRAINT pk_class_packages PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS class_wait_list (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   user_id UUID,
   classes_id UUID,
   waited_at TIMESTAMP WITH TIME ZONE,
   CONSTRAINT pk_class_wait_list PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS password_resets (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   user_id UUID,
   token VARCHAR(255) NOT NULL,
   expire_at TIMESTAMP WITH TIME ZONE,
   is_used BOOLEAN DEFAULT FALSE,
   CONSTRAINT pk_password_resets PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   is_verified BOOLEAN DEFAULT FALSE NOT NULL,
   verification_token VARCHAR(255),
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (email);

CREATE TABLE IF NOT EXISTS user_packages (
   id UUID NOT NULL,
   created_date TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_date TIMESTAMP WITH TIME ZONE,
   user_id UUID,
   classes_package_id UUID,
   default_credit_amount INTEGER,
   used_credit_amount INTEGER,
   expire_at TIMESTAMP WITH TIME ZONE,
   purchase_at TIMESTAMP WITH TIME ZONE,
   status VARCHAR(20) DEFAULT 'active',
   CONSTRAINT pk_user_packages PRIMARY KEY (id)
);

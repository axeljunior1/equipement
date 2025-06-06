

CREATE TABLE app_user (
                          id SERIAL PRIMARY KEY,
                          username VARCHAR(50) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(30) NOT NULL,
                          tenant_id VARCHAR(100) NOT NULL,

                          CONSTRAINT fk_app_user_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE
);

CREATE TABLE payment_transaction (
                                     id SERIAL PRIMARY KEY,
                                     client_phone VARCHAR(20) NOT NULL,
                                     amount NUMERIC(12,2) NOT NULL,
                                     status VARCHAR(20) DEFAULT 'PENDING',
                                     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     tenant_id VARCHAR(100) NOT NULL,

                                     CONSTRAINT fk_pay_transac_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE
);

CREATE TABLE wallet (
                        id SERIAL PRIMARY KEY,
                        balance NUMERIC(12,2) DEFAULT 0,
                        tenant_id VARCHAR(100) unique NOT NULL ,

                        CONSTRAINT fk_wallet_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE
);




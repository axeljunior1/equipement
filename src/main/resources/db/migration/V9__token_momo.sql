CREATE TABLE momo_token (
                            id SERIAL PRIMARY KEY,
                            access_token TEXT NOT NULL,
                            token_type VARCHAR(20) NOT NULL DEFAULT 'Bearer',
                            expires_at TIMESTAMP NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                tenant_id VARCHAR(100) NOT NULL,

                            CONSTRAINT fk_momo_token_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE
);

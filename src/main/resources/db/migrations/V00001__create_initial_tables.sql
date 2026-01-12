--liquibase formatted sql

--changeset dtn1999:create_initial_tables
CREATE SCHEMA IF NOT EXISTS listing_service;

DROP TABLE IF EXISTS SITE;
DROP TABLE IF EXISTS MEDIA;
DROP TABLE IF EXISTS SITE_MEDIA;
DROP TABLE IF EXISTS PLOT_OF_LAND;
DROP TABLE IF EXISTS LISTING_ADDRESS;

--comment: Table listing address
CREATE TABLE LISTING_ADDRESS
(
    id         BIGSERIAL    NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN      NOT NULL DEFAULT FALSE,
    country    VARCHAR(255) NOT NULL,
    state      VARCHAR(255) NOT NULL,
    city       VARCHAR(500) NOT NULL,
    street     VARCHAR(500),
    zip_code   VARCHAR(255),

--  Constraints
    CONSTRAINT pk_address PRIMARY KEY (id)
);

--comment: table media
CREATE TABLE MEDIA
(
    id         BIGSERIAL    NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN      NOT NULL DEFAULT FALSE,
    type       VARCHAR(50)  NOT NULL,
    url        VARCHAR(500) NOT NULL,
    name       VARCHAR(500),
    alt_text   VARCHAR(500),
    externalId VARCHAR(500),
    ownership  VARCHAR(50),


--  Constraints
    CONSTRAINT pk_media PRIMARY KEY (id)
);

--comment: Table site
CREATE TABLE SITE
(
    id                          BIGSERIAL NOT NULL,
    created_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted                     BOOLEAN   NOT NULL DEFAULT FALSE,
    name                        VARCHAR(255),
    parcel_area                 NUMERIC(10, 3),
    total_site_area             NUMERIC(10, 3),
    number_of_parcels           INTEGER,
    price_per_parcel            BIGINT,
    monthly_taxes_fee           BIGINT,
    annual_interest_rate        NUMERIC(10, 3),
    contract_processing_fee     BIGINT,
    default_loan_term_in_months INTEGER,
    down_payment_per_parcel     BIGINT,
    monthly_payment_per_parcel  BIGINT,
    description                 TEXT,
    state                       VARCHAR(255)       DEFAULT 'DRAFT',

--  Foreign keys
    address_id                  BIGINT,
    cover_image_id              BIGINT,
    organization_id             BIGINT,

--  Constraints
    CONSTRAINT pk_site_id PRIMARY KEY (id),
    CONSTRAINT uq_site_name UNIQUE (name),
    CONSTRAINT fk_site_cover_media FOREIGN KEY (cover_image_id) REFERENCES MEDIA (id),
    CONSTRAINT fk_listing_address FOREIGN KEY (address_id) REFERENCES LISTING_ADDRESS (id)
);

CREATE TABLE SITE_MEDIA
(
    site_id  BIGINT NOT NULL,
    media_id BIGINT NOT NULL,

--  Constraints
    CONSTRAINT pk_site_media PRIMARY KEY (site_id, media_id),
    CONSTRAINT fk_site_media_site FOREIGN KEY (site_id) REFERENCES SITE (id),
    CONSTRAINT fk_site_media_media FOREIGN KEY (media_id) REFERENCES MEDIA (id)
);

--rollback DROP TABLE SITE;
--rollback DROP TABLE MEDIA;
--rollback DROP TABLE LISTING_ADDRESS;

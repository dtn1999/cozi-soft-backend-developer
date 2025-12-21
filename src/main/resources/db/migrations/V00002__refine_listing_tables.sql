--liquibase formatted sql

--changeset dtn1999:refine_listing_tables
--comment: drop not null constrains from `parcel_id` column in `PLOT_OF_LAND` table

ALTER TABLE PLOT_OF_LAND
    ALTER COLUMN parcel_id DROP NOT NULL;

--comment: drop `owner_id`, `price`, `down_payment`, `monthly_payment` columns from `PLOT_OF_LAND` table

ALTER TABLE PLOT_OF_LAND DROP COLUMN price;
ALTER TABLE PLOT_OF_LAND DROP COLUMN owner_id;
ALTER TABLE PLOT_OF_LAND DROP COLUMN down_payment;
ALTER TABLE PLOT_OF_LAND DROP COLUMN monthly_payment;

--rollback ALTER TABLE PLOT_OF_LAND ALTER COLUMN parcel_id SET NOT NULL;
--rollback ALTER TABLE PLOT_OF_LAND ADD COLUMN price BIGINT;
--rollback ALTER TABLE PLOT_OF_LAND ADD COLUMN owner_id BIGINT;
--rollback ALTER TABLE PLOT_OF_LAND ADD COLUMN down_payment BIGINT;
--rollback ALTER TABLE PLOT_OF_LAND ADD COLUMN monthly_payment BIGINT;

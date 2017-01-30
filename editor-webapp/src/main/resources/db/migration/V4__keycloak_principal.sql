ALTER TABLE editor_user ALTER COLUMN surname DROP NOT NULL;
ALTER TABLE editor_user ADD principal VARCHAR(45);
CREATE UNIQUE INDEX editor_user_principal_uindex ON editor_user(principal);
--liquibase formatted sql

--changeset andrey:create-table-api_spec

create table api_spec (
    id uuid primary key DEFAULT uuid_generate_v4(),
    name text not null,
    version text not null,
    spec text not null,
    is_valid boolean not null,
    validation_log text,
    created_at timestamp without time zone default now()
);

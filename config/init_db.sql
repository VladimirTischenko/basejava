create table resume
(
    uuid      varchar not null
        constraint resume_pk
            primary key,
    full_name text    not null
);

create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    resume_uuid varchar not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade,
    type        text    not null,
    value       text    not null
);

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table section
(
    id          serial
        constraint section_pk
            primary key,
    resume_uuid varchar not null
        constraint section_resume_uuid_fk
            references resume
            on delete cascade,
    type        text    not null,
    text        text
);

create unique index section_uuid_type_uindex
    on section (resume_uuid, type);
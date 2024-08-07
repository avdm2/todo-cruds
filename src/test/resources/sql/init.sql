create schema if not exists todo;

create table if not exists todo.tasks
(
    id          integer primary key not null,
    title       varchar(128)        not null,
    description text,
    created_at  timestamp           not null,
    due_to      timestamp,
    priority    character varying(16),
    status      character varying(16),

    constraint tasks_priority_check check (priority::text = any (array ['NO_PRIORITY'::character varying,
        'LOW'::character varying, 'MEDIUM'::character varying, 'HIGH'::character varying]::text[])),
    constraint tasks_status_check check (status::text = any (array ['NOT_SELECTED'::character varying,
        'IN_PROGRESS'::character varying, 'DONE'::character varying]::text[])),
    constraint tasks_due_to_check check (due_to >= created_at)
);

create sequence if not exists todo.tasks_id_seq
    start 1 increment 1 minvalue 1 maxvalue 2147483647 cache 1
    owned by todo.tasks.id;

alter table todo.tasks alter column id set default  nextval('todo.tasks_id_seq'::regclass);
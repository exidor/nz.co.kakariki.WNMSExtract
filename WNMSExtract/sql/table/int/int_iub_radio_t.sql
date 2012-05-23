create table int_iub_radio_t
(
  id varchar(5) NOT NULL,
  ts timestamp without time zone NOT NULL,
  rls_uns double precision,
  rls_req double precision,
  constraint int_iub_radio_t_pk primary key (id, ts)
);
create table int_rnc_t (
id varchar(12),
ts timestamp,
acc_hspa double precision, 
ret_hsxpa double precision,
acc_voicerab double precision,
ret_voice double precision,
constraint int_rnc_t_pk primary key (id,ts)
);

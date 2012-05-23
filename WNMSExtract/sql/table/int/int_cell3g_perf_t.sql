create table int_cell3g_perf_t (
rcid varchar(12),
rfid int,
ucid varchar(12),
ts timestamp,
acc_hspa double precision, 
ret_hsxpa double precision,
acc_voicerab double precision,
ret_voice double precision,
constraint int_cell3g_perf_t_pk primary key (rcid,rfid,ucid,ts)
);

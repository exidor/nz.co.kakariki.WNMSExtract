create table int_cell3g_t (
rcid varchar(12),
rfid int,
ucid varchar(12),
ts timestamp,
ul_load_tt double precision, 
ul_load_edch double precision,
pct_pwr_used double precision,
clfs double precision,
constraint int_cell3g_t_pk primary key (rcid,rfid,ucid,ts)
);
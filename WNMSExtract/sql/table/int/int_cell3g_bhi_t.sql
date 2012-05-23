create table int_cell3g_bhi_t (
rcid varchar(12),
rfid int,
ucid varchar(12),
ts timestamp,
rrc_bhi double precision, 
speech_bhi double precision,
data_bhi double precision,
combined_bhi double precision,
constraint int_cell3g_bhi_t_pk primary key (rcid,rfid,ucid,ts)
);

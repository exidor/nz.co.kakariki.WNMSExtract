PLACEHOLDER
create table report_cell3gsummary_t (
id varchar(12) not null,
speech_ulta double precision,
speech_ulda double precision,
speech_ppua double precision,
speech_clfsa double precision,
data_ulta double precision,
data_ulda double precision,
data_ppua double precision,
data_clfsa double precision,
combined_ulta double precision,
combined_ulda double precision,
combined_ppua double precision,
combined_clfsa double precision,
attempts_ulta double precision,
attempts_ulda double precision,
attempts_ppua double precision,
attempts_clfsa double precision,
constraint report_cell3g_t_pk primary key (id)
)
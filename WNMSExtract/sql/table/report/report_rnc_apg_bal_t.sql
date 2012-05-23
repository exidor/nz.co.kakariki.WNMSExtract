create table report_rnc_apg_bal_t (
id varchar(12),
ap varchar(4),
wk timestamp,
diff double precision,
constraint report_rnc_apg_bal_t_pk primary key (id,ap,wk)
);
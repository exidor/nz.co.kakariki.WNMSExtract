create table report_borg_daily_t (
id varchar(12),
ap varchar(5),
dy timestamp,
aocc double precision,
constraint report_borg_daily_t_pk primary key (id,ap,dy)
);
create table raw_15min_rncap(
apid varchar(12),
ts timestamp,
vsapcpuutilizationavg float,
vsapmemoryutilization float,
constraint raw_15min_rncap_pk primary key (apid,ts)
);
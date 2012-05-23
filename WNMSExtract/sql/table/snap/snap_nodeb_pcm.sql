-- Count of PCM links per NodeB 
create table snap_nodeb_pcm(
beid varchar(8),
pcm int,
constraint snap_nodeb_pcm_pk primary key (beid) 
);
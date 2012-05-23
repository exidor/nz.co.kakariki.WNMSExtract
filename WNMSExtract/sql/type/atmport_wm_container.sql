-- wm = weekly max (vs bh determined)
create type atmport_wm_container as (
id varchar(8), 
ts timestamp,
mval double precision ,
aval double precision
);
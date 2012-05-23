create or replace rule int_atmport_rl as on update to int_atmport
do 
--insert into int_atmport_t(inid,rid,iid,apid,name,ts,dl_av,ul_av,dl_mx,ul_mx)
--values (new.inid,new.rid,new.iid,new.apid,new.name,new.ts,new.dl_av,new.ul_av,new.dl_mx,new.ul_mx);
insert into int_atmport(inid,rid,iid,apid,name,ts,dl_av,ul_av,dl_mx,ul_mx)
values('aaa',1,'bbb',2,'aabb',now()::timestamp,1.1,2.2,3.3,4.4);
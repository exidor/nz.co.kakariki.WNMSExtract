create trigger int_atmport_tg after insert or update on raw_inode_atmport
for each row execute procedure int_atmport_tf();

create trigger int_etherlp_tg after insert or update on raw_inode_ethernet
for each row execute procedure int_etherlp_tf();

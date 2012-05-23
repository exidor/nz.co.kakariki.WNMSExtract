create trigger iub_wmm_trig after insert or update on raw_iub
for each row execute procedure iub_wmm_tfunc();

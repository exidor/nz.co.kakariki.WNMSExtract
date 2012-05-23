-- create type regrCurveFit_container as (a float, b float, r float);
create or replace function regrCurveFit(type smallint) returns setof regrCurveFit_container as $$
declare
	x1 float;
	x2 float;
	y1 float,
	y2 float;
	xy float;
	a float;
	b float;
	r float;
	d float;
begin
	select 
		count(*) as n,
		case when type = 2 then sum(extract(epoch from ts))
			when type = 3 then sum(log(extract(epoch from ts)))
			when type = 4 then sum(log(extract(epoch from ts)))
		else sum(ts) end as x1,
		case when type = 2 then sum(extract(epoch from ts)*extract(epoch from ts))
			when type = 3 then sum(log(extract(epoch from ts))*log(extract(epoch from ts)))
			when type = 4 then sum(log(extract(epoch from ts))*log(extract(epoch from ts)))
		else sum(extract(epoch from ts)*extract(epoch from ts)) end as x2,
		case when type = 2 then sum(log(occ))
			when type = 3 then sum(occ)
			when type = 4 then sum(log(occ))
		else sum(y) end as y1,
		case when type = 2 then sum(log(occ)*log(occ))
			when type = 3 then sum(occ*occ)
			when type = 4 then sum(log(occ)*log(occ))
		else sum(extract(epoch from ts)) end as y2,
		case when type = 2 then sum(extract(epoch from ts)*log(occ))
			when type = 3 then sum(log(extract(epoch from ts))*occ)
			when type = 4 then sum(log(extract(epoch from ts))*log(occ))
		else sum(extract(epoch from ts)*occ) end as xy,
		(n*x2)-(x1*x1) as d
	into temp_rcf
	from report_rnc_apg_t;
	
	if d=0 then return;
	
	select	
		(x2*y1 - x1*xy) / d as a,
		(n*xy - x1*y1) / d as b,
		(a*y1 + b*xy - y1*y1 / n) / (y2 - (y1*y1) / n) as r
	from temp_rcf;
end;
$$ language 'sql';

create function rcf
(
	@Type TINYINT
)
RETURNS @p TABLE (A DECIMAL(38, 10), b DECIMAL(38, 10), R2 DECIMAL(38, 10))
AS
/*
Type = 1	Linear		y = a + b*x
Type = 2	Exponential	y = a*e^(b*x)   nb a > 0
Type = 3	Logarithmic	y = a + b*ln(x)
Type = 4	Power		y = a*x^b	nb a > 0
*/
BEGIN
	DECLARE	@n DECIMAL(38, 10),
		@x DECIMAL(38, 10),
		@x2 DECIMAL(38, 10),
		@y DECIMAL(38, 10),
		@xy DECIMAL(38, 10),
		@y2 DECIMAL(38, 10),
		@d DECIMAL(38, 10),
		@a DECIMAL(38, 10),
		@b DECIMAL(38, 10),
		@r2 DECIMAL(38, 10)

	SELECT	@n =	COUNT(*),
		@x =	CASE
				WHEN @Type = 2 THEN SUM(x)
				WHEN @Type = 3 THEN SUM(LOG(x))
				WHEN @Type = 4 THEN SUM(LOG(x))
				ELSE SUM(x)
			END,
		@x2 =	CASE
				WHEN @Type = 2 THEN SUM(x * x)
				WHEN @Type = 3 THEN SUM(LOG(x) * LOG(x))
				WHEN @Type = 4 THEN SUM(LOG(x) * LOG(x))
				ELSE SUM(x * x)
			END,
		@y =	CASE
				WHEN @Type = 2 THEN SUM(LOG(y))
				WHEN @Type = 3 THEN SUM(y)
				WHEN @Type = 4 THEN SUM(LOG(y))
				ELSE SUM(y)
			END,
		@xy =	CASE
				WHEN @Type = 2 THEN SUM(x * LOG(y))
				WHEN @Type = 3 THEN SUM(LOG(x) * y)
				WHEN @Type = 4 THEN SUM(LOG(x) * LOG(y))
				ELSE SUM(x * y)
			END,
		@y2 =	CASE
				WHEN @Type = 2 THEN SUM(LOG(y) * LOG(y))
				WHEN @Type = 3 THEN SUM(y * y)
				WHEN @Type = 4 THEN SUM(LOG(y) * LOG(y))
				ELSE SUM(y * y)
			END,
		@d =	@n * @x2 - @x * @x		
	FROM	cf

	IF @d = 0
		RETURN

	SELECT	@a = (@x2 * @y - @x * @xy) / @d,
		@b = (@n * @xy - @x * @y) / @d,
		@r2 = (@a * @y + @b * @xy - @y * @y / @n) / (@y2 - @y * @y / @n)

	INSERT	@p
	SELECT	CASE
			WHEN @Type = 2 THEN EXP(@a)
			WHEN @Type = 3 THEN @a
			WHEN @Type = 4 THEN EXP(@a)
			ELSE @a
		END,
		@b,
		@r2

	RETURN
END
CREATE FUNCTION dbo.fnBestFit
(
)
RETURNS @p TABLE (Type TINYINT, A DECIMAL(38, 10), b DECIMAL(38, 10), R2 DECIMAL(38, 10))
AS

BEGIN
	INSERT	@p
	SELECT	1,
		A,
		b,
		R2
	FROM	dbo.fnCurveFitting(1)

	INSERT	@p
	SELECT	2,
		A,
		b,
		R2
	FROM	dbo.fnCurveFitting(2)

	INSERT	@p
	SELECT	3,
		A,
		b,
		R2
	FROM	dbo.fnCurveFitting(3)

	INSERT	@p
	SELECT	4,
		A,
		b,
		R2
	FROM	dbo.fnCurveFitting(4)

	DELETE
	FROM	@p
	WHERE	R2 <> (SELECT MAX(R2) FROM @p)

	RETURN
END
create table raw_15min_cell3g(
clid varchar(12),
ts timestamp,
vsdedicateddownlinkkbytesrlcreferencecelldlrabhsdpa float,�
vsdedicateduplinkkbytesrlcreferencecellulrabhsupa float,
rabattestabpstrchndchdch float,�
rabattestabpstrchndchhsdsch float,
rabattestabpstrchnedchhsdsch float,
vsrabmeancsvsumcum float,
vsrabmeancsvsumnbevt float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabother float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib128 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib16 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib256 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib32 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib384 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib64 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsib8 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsstr128 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsstr256 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsstr384 float,
vsdedicateddownlinkkbytesrlcreferencecelldlrabpsstrother float,
vsdedicateduplinkkbytesrlcreferencecellulrabother float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsib128 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsib16 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsib32 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsib384 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsib64 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsib8 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsstr16 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsstr64 float,
vsdedicateduplinkkbytesrlcreferencecellulrabpsstrother float,
constraint raw_15min_cell3g_pk primary key (clid,ts)
);
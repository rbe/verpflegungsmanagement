myTrustedSnapSaveAs = app.trustedFunction(function(doc,path)
{
app.beginPriv();
var xfile = '..\\out\\' + path;
doc.saveAs(xfile);
app.endPriv();
});
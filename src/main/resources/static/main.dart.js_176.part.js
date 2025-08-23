((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,A,C,B={
c4I(d){var x=J.M(d),w=x.h(d,"title")
w=w==null?null:J.T(w)
if(w==null)w=""
x=x.h(d,"url")
x=x==null?null:J.T(x)
return new B.ny(w,x==null?"":x)},
c4H(d){var x,w,v=J.M(d),u=v.h(d,"found")
u=u==null?null:J.T(u)
if(u==null)u=""
x=v.h(d,"loginUrl")
x=x==null?null:J.T(x)
if(x==null)x=""
w=v.h(d,"loginUi")
w=w==null?null:J.T(w)
if(w==null)w=""
v=v.h(d,"checkKeyWord")
if(v!=null)J.T(v)
return new B.ab_(u,x,w)},
ny:function ny(d,e){this.a=d
this.b=e},
ab_:function ab_(d,e,f){this.a=d
this.c=e
this.d=f},
Hs(d,e){var x=0,w=A.i(y.h),v,u,t,s,r,q,p
var $async$Hs=A.d(function(f,g){if(f===1)return A.e(g,w)
while(true)switch(x){case 0:q="ExploreUrl"+d+A.m(E.fu())
x=e!=="1"?3:4
break
case 3:x=5
return A.c(D.hH(q),$async$Hs)
case 5:u=g
if(u!=null&&u.length!==0){v=B.c4H(J.z(C.m.M(0,u),"data"))
x=1
break}case 4:t=y.g
s=A.N(["bookSourceUrl",d,"need",e],t,t)
p=C.r
x=6
return A.c(F.c4(I.bb("/getBookSourcesExploreUrl"),s),$async$Hs)
case 6:u=p.M(0,g)
r=C.m.M(0,u)
t=J.M(r)
x=t.h(r,"isSuccess")?7:9
break
case 7:x=10
return A.c(G.hR(q,u),$async$Hs)
case 10:v=B.c4H(t.h(r,"data"))
x=1
break
x=8
break
case 9:throw A.k(A.ay(H.bk(t.h(r,"errorMsg"))))
case 8:case 1:return A.f(v,w)}})
return A.h($async$Hs,w)}},D,E,F,G,H,I
J=c[1]
A=c[0]
C=c[2]
B=a.updateHolder(c[30],B)
D=c[71]
E=c[98]
F=c[95]
G=c[86]
H=c[89]
I=c[85]
B.ny.prototype={
cj(){return A.N(["title",this.a,"url",this.b],y.g,y.b)}}
B.ab_.prototype={}
var z=a.updateTypes(["am<j,@>()"]);(function installTearOffs(){var x=a._instance_0u
x(B.ny.prototype,"gfB","cj",0)})();(function inheritance(){var x=a.inheritMany
x(A.w,[B.ny,B.ab_])})()
var y={h:A.D("ab_"),g:A.D("j"),b:A.D("@")}};
(a=>{a["DS6UfGfdPKirdjj8mexe8iIkdoY="]=a.current})($__dart_deferred_initializers__);
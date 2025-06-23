((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_176",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={
bRP(d){var x=J.L(d),w=x.h(d,"title")
w=w==null?null:J.P(w)
if(w==null)w=""
x=x.h(d,"url")
x=x==null?null:J.P(x)
return new A.oZ(w,x==null?"":x)},
bRO(d){var x,w,v=null,u=J.L(d),t=u.h(d,"found")
t=t==null?v:J.P(t)
if(t==null)t=""
x=u.h(d,"loginUrl")
x=x==null?v:J.P(x)
if(x==null)x=""
w=u.h(d,"loginUi")
w=w==null?v:J.P(w)
if(w==null)w=""
u=u.h(d,"checkKeyWord")
u=u==null?v:J.P(u)
return new A.a6h(t,u==null?"":u,x,w)},
oZ:function oZ(d,e){this.a=d
this.b=e},
a6h:function a6h(d,e,f,g){var _=this
_.a=d
_.b=e
_.c=f
_.d=g},
EZ(d,e){return A.c5m(d,e)},
c5m(d,e){var x=0,w=B.i(y.h),v,u,t,s,r,q,p
var $async$EZ=B.d(function(f,g){if(f===1)return B.e(g,w)
while(true)switch(x){case 0:q="ExploreUrl"+d
x=e!=="1"?3:4
break
case 3:x=5
return B.c(G.iJ(q),$async$EZ)
case 5:u=g
if(u!=null&&u.length!==0){v=A.bRO(J.D(C.m.N(0,u),"data"))
x=1
break}case 4:t=y.g
s=B.Q(["bookSourceUrl",d,"need",e],t,t)
p=C.t
x=6
return B.c(E.cf(F.bg("/getBookSourcesExploreUrl"),s),$async$EZ)
case 6:u=p.N(0,g)
r=C.m.N(0,u)
t=J.L(r)
x=t.h(r,"isSuccess")?7:9
break
case 7:x=10
return B.c(H.je(q,u),$async$EZ)
case 10:v=A.bRO(t.h(r,"data"))
x=1
break
x=8
break
case 9:throw B.k(B.ay(D.bu(t.h(r,"errorMsg"))))
case 8:case 1:return B.f(v,w)}})
return B.h($async$EZ,w)}},D,E,F,G,H
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[28],A)
D=c[98]
E=c[100]
F=c[82]
G=c[65]
H=c[72]
A.oZ.prototype={}
A.a6h.prototype={}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(B.w,[A.oZ,A.a6h])})()
var y={h:B.C("a6h"),g:B.C("l")}};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_176",e:"endPart",h:b})})($__dart_deferred_initializers__,"W811uwONhhj+hNrBWxgQQ+xZSKE=");
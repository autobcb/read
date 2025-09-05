((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,A,C,E,D,B={bOL:function bOL(d){var _=this
_.a=d
_.e=_.b=""
_.r=null},bOM:function bOM(d){this.a=d},bON:function bON(d){this.a=d},
cBX(d){var x,w
if(d==null)return null
try{d.gbE()
x=d.T()
return x}catch(w){if(!y.A.b(A.G(w)))throw w}try{d.gfA()
x=d.cj()
return x}catch(w){if(!y.A.b(A.G(w)))throw w}return J.U(d)},
c0z(d){var x=new B.bOL([])
x.r=B.cEQ()
return x.Xq("",A.N(["",d],y.w,y.b))}}
J=c[1]
A=c[0]
C=c[2]
E=c[72]
D=c[176]
B=a.updateHolder(c[68],B)
B.bOL.prototype={
Vv(d){var x,w,v,u,t,s,r,q,p,o=A.N(["'",0.1,'"',0.2],y.w,y.b)
$label0$1:for(x=d.length,w="",v=0;v<x;++v){u=d[v]
switch(u){case"'":case'"':o.p(0,u,o.h(0,u)+1)
w+=u
continue $label0$1
case"\x00":t=v+1
s=!1
if(t<x){t=d[t]
r=A.bY("[0-9]",!0,!1,!1,!1)
t=r.b.test(t)}else t=s
if(t){w+="\\x00"
continue $label0$1}break}if(D.jy.aI(0,u)){t=D.jy.h(0,u)
t.toString
w+=t
continue $label0$1}if(u.charCodeAt(0)<32){q=C.f.h1(u.charCodeAt(0),16)
w+="\\x"+C.c.bN("00"+q,q.length)
continue $label0$1}w+=u}p=new A.bJ(o,A.B(o).i("bJ<1>")).o4(0,new B.bOM(o))
x=A.bY(p,!0,!0,!1,!1)
t=D.jy.h(0,p)
t.toString
return p+A.dU(w,x,t)+p},
Xq(d,e){var x,w=J.z(e,d),v=new B.bON(this),u=v.$1(w)
if(u!=null)return u
x=this.r
if(x!=null)w=x.$1(w)
u=v.$1(w)
if(u!=null)return u
throw A.k(A.az("Cannot stringify "+A.m(w)))},
azb(d){var x,w,v,u,t=d.length
if(t===0)return this.Vv(d)
x=d[0]
if(!E.c0v(x))return this.Vv(d)
for(w=x.length;w<t;++w){v=d[w]
u=A.bY("[a-z0-9A-Z_\\u200C\\u200D$]",!0,!1,!1,!1)
if(!u.b.test(v)){u=$.c11()
v=u.b.test(v)}else v=!0
if(!v)return this.Vv(d)}return d},
azc(d){var x,w,v,u,t,s,r,q,p,o=this,n=o.a
if(C.b.q(n,d))throw A.k(A.az("Converting circular structure to JSON5"))
n.push(d)
x=o.b
o.b=x+o.e
w=J.uE(d)
v=[]
for(u=J.bj(w);u.C();){t=u.gU(u)
s=o.Xq(t,d)
r=o.azb(J.U(t))+":"
v.push((o.e!==""?r+" ":r)+s)}if(v.length===0)q="{}"
else if(o.e==="")q="{"+C.b.c1(v,",")+"}"
else{p=C.b.c1(v,",\n"+o.b)
q="{\n"+o.b+p+",\n"+x+"}"}n.pop()
o.b=x
return q},
a98(d){var x,w,v,u,t,s,r=this,q=r.a
if(C.b.q(q,d))throw A.k(A.az("Converting circular structure to JSON5"))
q.push(d)
x=r.b
r.b=x+r.e
w=[]
for(v=J.M(d),u=0;u<v.gD(d);++u)w.push(r.Xq(u,d))
if(w.length===0)t="[]"
else if(r.e==="")t="["+C.b.c1(w,",")+"]"
else{s=C.b.c1(w,",\n"+r.b)
t="[\n"+r.b+s+",\n"+x+"]"}q.pop()
r.b=x
return t}}
var z=a.updateTypes(["w?(@)"])
B.bOM.prototype={
$2(d,e){var x=this.a,w=x.h(0,d)
w.toString
x=x.h(0,e)
x.toString
return w<x?d:e},
$S:96}
B.bON.prototype={
$1(d){var x=this
if(d==null)return"null"
switch(d){case!0:return"true"
case!1:return"false"}if(typeof d=="string")return x.a.Vv(d)
if(typeof d=="number")return C.e.j(d)
if(y.i.b(d))return x.a.a98(d)
if(y.B.b(d))return x.a.azc(d)
if(y.F.b(d))return x.a.a98(J.pV(d))
return null},
$S:519};(function installTearOffs(){var x=a._static_1
x(B,"cEQ","cBX",0)})();(function inheritance(){var x=a.inherit
x(B.bOL,A.w)
x(B.bOM,A.e7)
x(B.bON,A.cX)})()
var y={F:A.D("K<@>"),i:A.D("v<@>"),B:A.D("am<@,@>"),A:A.D("p7"),w:A.D("j"),b:A.D("@")}};
(a=>{a["NKxZp8wcsOLvv7QPlC0d9qur4Ho="]=a.current})($__dart_deferred_initializers__);
((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_19",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,A,C,B={
o7(d){var x,w,v,u,t="0123456789abcdef",s=d.length,r=new Uint8Array(s*2)
for(x=0,w=0;x<s;++x){v=d[x]
u=w+1
r[w]=t.charCodeAt(v>>>4&15)
w=u+1
r[u]=t.charCodeAt(v&15)}return A.hz(r,0,null)},
uV:function uV(d){this.a=d},
aKS:function aKS(){this.a=null},
RU:function RU(){},
ab4:function ab4(){},
asL:function asL(){},
bv6:function bv6(d,e,f,g,h){var _=this
_.w=d
_.a=e
_.b=f
_.c=g
_.d=0
_.e=h
_.f=!1},
L9(){var x=0,w=A.i(y.f),v=1,u=[],t,s,r,q,p
var $async$L9=A.d(function(d,e){if(d===1){u.push(e)
x=v}while(true)switch(x){case 0:v=3
s=G.bXL()
$.csk=s
p=$
x=6
return A.c(s.p7(0,"BookStorage",new B.b9n(),1),$async$L9)
case 6:p.L8=e
v=1
x=5
break
case 3:v=2
q=u.pop()
t=A.E(q)
A.W("IndexedDB \u521d\u59cb\u5316\u5931\u8d25: "+A.m(t))
x=5
break
case 2:x=1
break
case 5:return A.f(null,w)
case 1:return A.e(u.at(-1),w)}})
return A.h($async$L9,w)},
b9n:function b9n(){},
LE:function LE(){},
asj:function asj(){},
LH:function LH(d,e){this.a=d
this.b=e},
Gr(){var x=0,w=A.i(y.n),v,u
var $async$Gr=A.d(function(d,e){if(d===1)return A.e(e,w)
while(true)switch(x){case 0:$.bYr()
x=3
return A.c(F.Iw.bQ("getApplicationCacheDirectory",null,!1,y.g),$async$Gr)
case 3:u=e
if(u==null)throw A.k(E.c2O("Unable to get application cache directory"))
v=E.uY(u)
x=1
break
case 1:return A.f(v,w)}})
return A.h($async$Gr,w)}},D,E,F,G
J=c[1]
A=c[0]
C=c[2]
B=a.updateHolder(c[73],B)
D=c[154]
E=c[75]
F=c[116]
G=c[74]
B.uV.prototype={
k(d,e){var x,w,v,u,t
if(e==null)return!1
if(e instanceof B.uV){x=this.a
w=e.a
v=x.length
if(v!==w.length)return!1
for(u=0,t=0;t<v;++t)u|=x[t]^w[t]
return u===0}return!1},
gv(d){return A.cy(this.a)},
j(d){return B.o7(this.a)}}
B.aKS.prototype={
gn(d){var x=this.a
x.toString
return x},
t(d,e){if(this.a!=null)throw A.k(A.a_("add may only be called once."))
this.a=e},
a3(d){if(this.a==null)throw A.k(A.a_("add must be called once."))}}
B.RU.prototype={
bk(d){var x,w=new B.aKS(),v=this.ix(w)
v.t(0,d)
v.a3(0)
x=w.a
x.toString
return x}}
B.ab4.prototype={
t(d,e){var x=this
if(x.f)throw A.k(A.a_("Hash.add() called after close()."))
x.d=x.d+J.aG(e)
x.e.F(0,e)
x.agr()},
a3(d){var x,w=this
if(w.f)return
w.f=!0
w.aNt()
w.agr()
x=w.a
x.t(0,new B.uV(w.aJd()))
x.a3(0)},
aJd(){var x,w,v,u,t,s,r
if(this.b===$.ip())return J.rj(C.hV.gb6(this.ga3Z()))
x=this.ga3Z()
w=x.byteLength
v=new Uint8Array(w)
u=J.kl(C.o.gb6(v))
for(w=x.length,t=u.$flags|0,s=0;s<w;++s){r=x[s]
t&2&&A.au(u,11)
u.setUint32(s*4,r,!1)}return v},
agr(){var x,w,v,u,t,s,r=this,q=r.e,p=J.kl(C.o.gb6(q.a)),o=r.c,n=C.f.iR(q.b,o.byteLength)
for(x=o.length,w=C.z===r.b,v=o.$flags|0,u=0;u<n;++u){for(t=0;t<x;++t){s=p.getUint32(u*o.byteLength+t*4,w)
v&2&&A.au(o)
o[t]=s}r.atw(o)}q.DS(q,0,n*o.byteLength)},
aNt(){var x,w,v,u,t,s,r,q,p,o,n=this,m=n.e
m.a1B(0,128)
x=n.d+1+8
w=n.c.byteLength
for(w=((x+w-1&-w)>>>0)-x,v=0;v<w;++v)m.a1B(0,0)
w=n.d
if(w>1125899906842623)throw A.k(A.aM("Hashing is unsupported for messages with more than 2^53 bits."))
u=w*8
t=m.b
m.F(0,new Uint8Array(8))
s=J.kl(C.o.gb6(m.a))
r=C.f.b5(u,4294967296)
q=u>>>0
m=n.b
w=s.$flags|0
p=C.z===m
o=t+4
if(m===C.aD){w&2&&A.au(s,11)
s.setUint32(t,r,p)
s.setUint32(o,q,p)}else{w&2&&A.au(s,11)
s.setUint32(t,q,p)
s.setUint32(o,r,p)}}}
B.asL.prototype={
ix(d){var x=new Uint32Array(4),w=new Uint8Array(0),v=new Uint32Array(16)
x[0]=1732584193
x[1]=4023233417
x[2]=2562383102
x[3]=271733878
return new A.FF(new B.bv6(x,d,C.z,v,new B.LH(w,0)))}}
B.bv6.prototype={
atw(d){var x,w,v,u,t,s,r,q=this.w,p=q[0],o=q[1],n=q[2],m=q[3]
for(x=p,w=0;w<64;++w,x=m,m=n,n=o,o=r){if(w<16){v=(o&n|~o&m)>>>0
u=w}else if(w<32){v=(m&o|~m&n)>>>0
u=(5*w+1)%16}else if(w<48){v=(o^n^m)>>>0
u=(3*w+5)%16}else{v=(n^(o|~m))>>>0
u=C.f.aQ(7*w,16)}t=(x+v>>>0)+(D.abm[w]+d[u]>>>0)>>>0
s=D.a4c[w]&31
r=o+((t<<s|C.f.mC(t,32-s))>>>0)>>>0}q.$flags&2&&A.au(q)
q[0]=x+p>>>0
q[1]=o+q[1]>>>0
q[2]=n+q[2]>>>0
q[3]=m+q[3]>>>0},
ga3Z(){return this.w}}
B.LE.prototype={
gC(d){return this.b},
h(d,e){if(e>=this.b)throw A.k(A.abT(e,this,null,null,null))
return this.a[e]},
p(d,e,f){var x
if(e>=this.b)throw A.k(A.abT(e,this,null,null,null))
x=this.a
x.$flags&2&&A.au(x)
x[e]=f},
sC(d,e){var x,w,v,u,t=this,s=t.b
if(e<s)for(x=t.a,w=x.$flags|0,v=e;v<s;++v){w&2&&A.au(x)
x[v]=0}else{s=t.a.length
if(e>s){if(s===0)u=new Uint8Array(e)
else u=t.a1C(e)
C.o.c_(u,0,t.b,t.a)
t.a=u}}t.b=e},
a1B(d,e){var x,w=this,v=w.b
if(v===w.a.length)w.akO(v)
v=w.a
x=w.b++
v.$flags&2&&A.au(v)
v[x]=e},
t(d,e){var x,w=this,v=w.b
if(v===w.a.length)w.akO(v)
v=w.a
x=w.b++
v.$flags&2&&A.au(v)
v[x]=e},
F(d,e){A.h2(0,"start")
this.b5d(e,0,null)},
b5d(d,e,f){var x,w,v
if(y.i.b(d))f=J.aG(d)
if(f!=null){this.b5f(this.b,d,e,f)
return}for(x=J.bf(d),w=0;x.B();){v=x.gS(x)
if(w>=e)this.a1B(0,v);++w}if(w<e)throw A.k(A.a_("Too few elements"))},
b5f(d,e,f,g){var x,w,v,u,t=this
if(y.i.b(e)){x=J.M(e)
if(f>x.gC(e)||g>x.gC(e))throw A.k(A.a_("Too few elements"))}w=g-f
v=t.b+w
t.b5e(v)
x=t.a
u=d+w
C.o.dH(x,u,t.b+w,x,d)
C.o.dH(t.a,d,u,e,f)
t.b=v},
b5e(d){var x,w=this
if(d<=w.a.length)return
x=w.a1C(d)
C.o.c_(x,0,w.b,w.a)
w.a=x},
a1C(d){var x=this.a.length*2
if(d!=null&&x<d)x=d
else if(x<8)x=8
return new Uint8Array(x)},
akO(d){var x=this.a1C(null)
C.o.c_(x,0,d,this.a)
this.a=x},
dH(d,e,f,g,h){var x=this.b
if(f>x)throw A.k(A.e2(f,0,x,null,null))
x=this.a
if(g instanceof B.LH)C.o.dH(x,e,f,g.a,h)
else C.o.dH(x,e,f,g,h)},
c_(d,e,f,g){return this.dH(0,e,f,g,0)}}
B.asj.prototype={}
B.LH.prototype={}
var z=a.updateTypes(["aY(Ay)"])
B.b9n.prototype={
$1(d){var x=d.gSi(d)
J.aBC(x,"storage")},
$S:z+0};(function inheritance(){var x=a.inheritMany,w=a.inherit
x(A.w,[B.uV,B.aKS,B.ab4])
w(B.RU,A.cj)
w(B.asL,B.RU)
w(B.bv6,B.ab4)
w(B.b9n,A.cX)
w(B.LE,A.a9)
w(B.asj,B.LE)
w(B.LH,B.asj)})()
A.cP(b.typeUniverse,JSON.parse('{"RU":{"cj":["x<r>","uV"]},"asL":{"cj":["x<r>","uV"],"cj.S":"x<r>","cj.T":"uV"},"LE":{"a9":["1"],"x":["1"],"b0":["1"],"K":["1"]},"asj":{"LE":["r"],"a9":["r"],"x":["r"],"b0":["r"],"K":["r"]},"LH":{"LE":["r"],"a9":["r"],"x":["r"],"b0":["r"],"K":["r"],"a9.E":"r","K.E":"r"}}'))
var y={n:A.G("nl"),b:A.G("t<r>"),i:A.G("x<@>"),g:A.G("j"),f:A.G("~")};(function constants(){var x=a.makeConstList
D.dF=new B.asL()
D.a4c=A.a(x([7,12,17,22,7,12,17,22,7,12,17,22,7,12,17,22,5,9,14,20,5,9,14,20,5,9,14,20,5,9,14,20,4,11,16,23,4,11,16,23,4,11,16,23,4,11,16,23,6,10,15,21,6,10,15,21,6,10,15,21,6,10,15,21]),y.b)
D.abm=A.a(x([3614090360,3905402710,606105819,3250441966,4118548399,1200080426,2821735955,4249261313,1770035416,2336552879,4294925233,2304563134,1804603682,4254626195,2792965006,1236535329,4129170786,3225465664,643717713,3921069994,3593408605,38016083,3634488961,3889429448,568446438,3275163606,4107603335,1163531501,2850285829,4243563512,1735328473,2368359562,4294588738,2272392833,1839030562,4259657740,2763975236,1272893353,4139469664,3200236656,681279174,3936430074,3572445317,76029189,3654602809,3873151461,530742520,3299628645,4096336452,1126891415,2878612391,4237533241,1700485571,2399980690,4293915773,2240044497,1873313359,4264355552,2734768916,1309151649,4149444226,3174756917,718787259,3951481745]),y.b)})();(function staticFields(){$.csk=null
$.L8=null})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_19",e:"endPart",h:b})})($__dart_deferred_initializers__,"4aKaCpx7hiGPjYa2hZ6KuQa7tMo=");
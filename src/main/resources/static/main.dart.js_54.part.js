((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={
A3(d,e,f){var x,w,v={}
v.a=0
x=[]
w=[]
v.a=e.length
C.b.E(x,e)
v.b=""
if(f!=null&&f.a!==0)f.a3(0,new A.b2z(v,w,x))
return J.c2w(d,new B.Jg(D.aq6,0,x,w,0))},
csl(d,e,f){var x,w=f==null||f.a===0
if(w){if(!!d.$0)return d.$0()
x=d[""+"$0"]
if(x!=null)return x.apply(d,e)}return A.csk(d,e,f)},
csk(d,e,f){var x,w,v,u,t,s,r,q,p,o,n,m,l,k=d.$R
if(0<k)return A.A3(d,e,f)
x=d.$D
w=x==null
v=!w?x():null
u=J.k5(d)
t=u.$C
if(typeof t=="string")t=u[t]
if(w){if(f!=null&&f.a!==0)return A.A3(d,e,f)
if(0===k)return t.apply(d,e)
return A.A3(d,e,f)}if(Array.isArray(v)){if(f!=null&&f.a!==0)return A.A3(d,e,f)
s=k+v.length
if(0>s)return A.A3(d,e,null)
if(0<s){r=v.slice(0-k)
q=B.V(e,y.e)
C.b.E(q,r)}else q=e
return t.apply(d,q)}else{if(0>k)return A.A3(d,e,f)
q=B.V(e,y.e)
p=Object.keys(v)
if(f==null)for(w=p.length,o=0;o<p.length;p.length===w||(0,B.S)(p),++o){n=v[p[o]]
if(D.vs===n)return A.A3(d,q,f)
C.b.t(q,n)}else{for(w=p.length,m=0,o=0;o<p.length;p.length===w||(0,B.S)(p),++o){l=p[o]
if(f.aI(0,l)){++m
C.b.t(q,f.h(0,l))}else{n=v[l]
if(D.vs===n)return A.A3(d,q,f)
C.b.t(q,n)}}if(m!==f.a)return A.A3(d,q,f)}return t.apply(d,q)}},
b2z:function b2z(d,e,f){this.a=d
this.b=e
this.c=f},
bFA:function bFA(){},
b2J:function b2J(){},
a84:function a84(){},
ang:function ang(){},
bfG:function bfG(d){this.a=d},
c9t(d){var x=d.length
if(x<16)throw B.k(B.j_("buffer too small: need 16: length="+x))
x=$.chV()
return x[d[0]]+x[d[1]]+x[d[2]]+x[d[3]]+"-"+x[d[4]]+x[d[5]]+"-"+x[d[6]]+x[d[7]]+"-"+x[d[8]]+x[d[9]]+"-"+x[d[10]]+x[d[11]]+x[d[12]]+x[d[13]]+x[d[14]]+x[d[15]]}},D
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[74],A)
D=c[122]
A.bFA.prototype={}
A.b2J.prototype={
a8n(){var x=this.aPn()
if(x.length!==16)throw B.k(B.az("The length of the Uint8list returned by the custom RNG must be 16."))
else return x}}
A.a84.prototype={
aPn(){var x,w,v=new Uint8Array(16)
for(x=0;x<16;x+=4){w=$.ceg().KH(C.e.dm(Math.pow(2,32)))
v[x]=w
v[x+1]=C.f.br(w,8)
v[x+2]=C.f.br(w,16)
v[x+3]=C.f.br(w,24)}return v}}
A.ang.prototype={
a85(){return new A.bfG(null).axq(null)},
LW(){var x,w=null
if(w==null)x=null
else x=w
if(x==null)x=$.chX().a8n()
w=x[6]
x.$flags&2&&B.as(x)
x[6]=w&15|64
x[8]=x[8]&63|128
return A.c9t(x)}}
A.bfG.prototype={
b7X(){if($.c9u)return
var x=$.chW().a8n()
$.c9x=B.a([x[0]|1,x[1],x[2],x[3],x[4],x[5]],y.b)
$.bZt=(x[6]<<8|x[7])&262143
$.c9u=!0},
axq(d){var x,w,v,u,t,s,r,q,p,o
this.b7X()
x=new Uint8Array(16)
w=$.bZt
v=Date.now()
u=$.c9w
t=u+1
s=$.c9v
u=v-s+(t-u)/1e4<0
if(u)w=w+1&16383
u=u||v>s
if(u)t=0
if(t>=1e4)throw B.k(B.az("uuid.v1(): Can't create more than 10M uuids/sec"))
$.c9v=v
$.c9w=t
$.bZt=w
v+=122192928e5
r=((v&268435455)*1e4+t)%4294967296
x[0]=r>>>24&255
x[1]=r>>>16&255
x[2]=r>>>8&255
x[3]=r&255
q=C.e.f7(v/4294967296*1e4)&268435455
x[4]=q>>>8&255
x[5]=q&255
x[6]=q>>>24&255
x[7]=q>>>16&255
x[8]=w>>>8&63
x[9]=w&255
x[6]=x[6]&15|16
x[8]=x[8]|128
p=$.c9x
for(o=0;o<6;++o)x[10+o]=p[o]
return A.c9t(x)}}
var z=a.updateTypes([])
A.b2z.prototype={
$2(d,e){var x=this.a
x.b=x.b+"$"+d
this.b.push(d)
this.c.push(e);++x.a},
$S:33};(function inheritance(){var x=a.inherit,w=a.inheritMany
x(A.b2z,B.e7)
w(B.w,[A.bFA,A.b2J,A.ang,A.bfG])
x(A.a84,A.b2J)})()
B.cQ(b.typeUniverse,JSON.parse('{"xD":{"xC":[]}}'))
var y={b:B.D("t<r>"),e:B.D("@")};(function constants(){D.fk=new A.ang()
D.vs=new A.bFA()
D.aq6=new B.j3("call")})();(function staticFields(){$.a65=B.bH()
$.c9x=B.a([],y.b)
$.bZt=0
$.c9v=0
$.c9w=0
$.c9u=!1})();(function lazyInitializers(){var x=a.lazy,w=a.lazyFinal
x($,"cNp","chW",()=>new A.a84())
x($,"cNq","chX",()=>new A.a84())
w($,"cNo","chV",()=>{var v,u=J.iw(256,B.D("j"))
for(v=0;v<256;++v)u[v]=C.c.f9(C.f.h1(v,16),2,"0")
return u})
w($,"cI1","ceg",()=>B.c7m())})()};
(a=>{a["a98WfhHhg2rh98s6ZPdIgMwY/NE="]=a.current})($__dart_deferred_initializers__);
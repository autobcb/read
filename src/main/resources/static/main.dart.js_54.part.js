((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={
A0(d,e,f){var x,w,v={}
v.a=0
x=[]
w=[]
v.a=e.length
C.b.E(x,e)
v.b=""
if(f!=null&&f.a!==0)f.a3(0,new A.b2h(v,w,x))
return J.c29(d,new B.Je(D.aq5,0,x,w,0))},
crU(d,e,f){var x,w=f==null||f.a===0
if(w){if(!!d.$0)return d.$0()
x=d[""+"$0"]
if(x!=null)return x.apply(d,e)}return A.crT(d,e,f)},
crT(d,e,f){var x,w,v,u,t,s,r,q,p,o,n,m,l,k=d.$R
if(0<k)return A.A0(d,e,f)
x=d.$D
w=x==null
v=!w?x():null
u=J.k3(d)
t=u.$C
if(typeof t=="string")t=u[t]
if(w){if(f!=null&&f.a!==0)return A.A0(d,e,f)
if(0===k)return t.apply(d,e)
return A.A0(d,e,f)}if(Array.isArray(v)){if(f!=null&&f.a!==0)return A.A0(d,e,f)
s=k+v.length
if(0>s)return A.A0(d,e,null)
if(0<s){r=v.slice(0-k)
q=B.U(e,y.e)
C.b.E(q,r)}else q=e
return t.apply(d,q)}else{if(0>k)return A.A0(d,e,f)
q=B.U(e,y.e)
p=Object.keys(v)
if(f==null)for(w=p.length,o=0;o<p.length;p.length===w||(0,B.S)(p),++o){n=v[p[o]]
if(D.vr===n)return A.A0(d,q,f)
C.b.t(q,n)}else{for(w=p.length,m=0,o=0;o<p.length;p.length===w||(0,B.S)(p),++o){l=p[o]
if(f.aI(0,l)){++m
C.b.t(q,f.h(0,l))}else{n=v[l]
if(D.vr===n)return A.A0(d,q,f)
C.b.t(q,n)}}if(m!==f.a)return A.A0(d,q,f)}return t.apply(d,q)}},
b2h:function b2h(d,e,f){this.a=d
this.b=e
this.c=f},
bFp:function bFp(){},
b2r:function b2r(){},
a7Z:function a7Z(){},
an9:function an9(){},
bfo:function bfo(d){this.a=d},
c93(d){var x=d.length
if(x<16)throw B.k(B.iY("buffer too small: need 16: length="+x))
x=$.chv()
return x[d[0]]+x[d[1]]+x[d[2]]+x[d[3]]+"-"+x[d[4]]+x[d[5]]+"-"+x[d[6]]+x[d[7]]+"-"+x[d[8]]+x[d[9]]+"-"+x[d[10]]+x[d[11]]+x[d[12]]+x[d[13]]+x[d[14]]+x[d[15]]}},D
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[74],A)
D=c[122]
A.bFp.prototype={}
A.b2r.prototype={
a8h(){var x=this.aP2()
if(x.length!==16)throw B.k(B.ay("The length of the Uint8list returned by the custom RNG must be 16."))
else return x}}
A.a7Z.prototype={
aP2(){var x,w,v=new Uint8Array(16)
for(x=0;x<16;x+=4){w=$.cdR().KE(C.e.dl(Math.pow(2,32)))
v[x]=w
v[x+1]=C.f.br(w,8)
v[x+2]=C.f.br(w,16)
v[x+3]=C.f.br(w,24)}return v}}
A.an9.prototype={
a8_(){return new A.bfo(null).ax4(null)},
LR(){var x,w=null
if(w==null)x=null
else x=w
if(x==null)x=$.chx().a8h()
w=x[6]
x.$flags&2&&B.as(x)
x[6]=w&15|64
x[8]=x[8]&63|128
return A.c93(x)}}
A.bfo.prototype={
b7E(){if($.c94)return
var x=$.chw().a8h()
$.c97=B.a([x[0]|1,x[1],x[2],x[3],x[4],x[5]],y.b)
$.bZ5=(x[6]<<8|x[7])&262143
$.c94=!0},
ax4(d){var x,w,v,u,t,s,r,q,p,o
this.b7E()
x=new Uint8Array(16)
w=$.bZ5
v=Date.now()
u=$.c96
t=u+1
s=$.c95
u=v-s+(t-u)/1e4<0
if(u)w=w+1&16383
u=u||v>s
if(u)t=0
if(t>=1e4)throw B.k(B.ay("uuid.v1(): Can't create more than 10M uuids/sec"))
$.c95=v
$.c96=t
$.bZ5=w
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
p=$.c97
for(o=0;o<6;++o)x[10+o]=p[o]
return A.c93(x)}}
var z=a.updateTypes([])
A.b2h.prototype={
$2(d,e){var x=this.a
x.b=x.b+"$"+d
this.b.push(d)
this.c.push(e);++x.a},
$S:34};(function inheritance(){var x=a.inherit,w=a.inheritMany
x(A.b2h,B.e7)
w(B.w,[A.bFp,A.b2r,A.an9,A.bfo])
x(A.a7Z,A.b2r)})()
B.cP(b.typeUniverse,JSON.parse('{"xz":{"xy":[]}}'))
var y={b:B.D("t<r>"),e:B.D("@")};(function constants(){D.fj=new A.an9()
D.vr=new A.bFp()
D.aq5=new B.j2("call")})();(function staticFields(){$.a60=B.bG()
$.c97=B.a([],y.b)
$.bZ5=0
$.c95=0
$.c96=0
$.c94=!1})();(function lazyInitializers(){var x=a.lazy,w=a.lazyFinal
x($,"cMU","chw",()=>new A.a7Z())
x($,"cMV","chx",()=>new A.a7Z())
w($,"cMT","chv",()=>{var v,u=J.it(256,B.D("j"))
for(v=0;v<256;++v)u[v]=C.c.f9(C.f.h2(v,16),2,"0")
return u})
w($,"cHw","cdR",()=>B.c6Y())})()};
(a=>{a["u9u/FambLGqywgqRNh998R9Yvh8="]=a.current})($__dart_deferred_initializers__);
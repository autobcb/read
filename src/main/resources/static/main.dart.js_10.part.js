((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={aqZ:function aqZ(d){this.a=0
this.b=d},ZH:function ZH(d){this.a=0
this.b=d},
cxz(){throw B.k(B.aK("Platform._environment"))},
cx6(d,e){throw B.k(B.aK("_IOService._dispatch"))},
caT(d,e,f){var x,w
if(y.c.b(d)&&J.c20(C.n.gb9(d))===d.length)return new A.aqk(d,e)
x=f-e
w=new Uint8Array(x)
C.n.dd(w,0,x,d,e)
return new A.aqk(w,0)},
bXr(){var x=$.ar.h(0,$.cix())
return x==null?null:x},
cxD(){var x=$.cxA
if(x==null)A.cxz()
x.toString
return x},
aqk:function aqk(d,e){this.a=d
this.b=e}}
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[103],A)
A.aqZ.prototype={
t(d,e){var x,w,v,u,t,s=this,r=J.M(e),q=r.gD(e)
if(q===0)return
x=s.a+q
if(s.b.length<x)s.aHu(x)
if(y.c.b(e))C.n.c_(s.b,s.a,x,e)
else for(w=0;w<q;++w){v=s.b
u=s.a
t=r.h(e,w)
v.$flags&2&&B.as(v)
v[u+w]=t}s.a=x},
aHu(d){var x,w,v,u=d*2
if(u<1024)u=1024
else{x=u-1
x|=C.f.br(x,1)
x|=x>>>2
x|=x>>>4
x|=x>>>8
u=((x|x>>>16)>>>0)+1}w=new Uint8Array(u)
v=this.b
C.n.c_(w,0,v.length,v)
this.b=w},
E4(){var x,w=this
if(w.a===0)return $.GZ()
x=J.dV(C.n.gb9(w.b),w.b.byteOffset,w.a)
w.a=0
w.b=$.GZ()
return x},
E5(){var x=this
if(x.a===0)return $.GZ()
return new Uint8Array(B.fg(J.dV(C.n.gb9(x.b),x.b.byteOffset,x.a)))},
gD(d){return this.a},
gae(d){return this.a===0},
gcm(d){return this.a!==0},
P(d){this.a=0
this.b=$.GZ()}}
A.ZH.prototype={
t(d,e){var x=y.c.b(e)?e:new Uint8Array(B.fg(e))
this.b.push(x)
this.a=this.a+x.length},
E4(){var x,w,v,u,t,s,r,q=this,p=q.a
if(p===0)return $.GZ()
x=q.b
w=x.length
if(w===1){v=x[0]
q.a=0
C.b.P(x)
return v}v=new Uint8Array(p)
for(u=0,t=0;t<x.length;x.length===w||(0,B.S)(x),++t,u=r){s=x[t]
r=u+s.length
C.n.c_(v,u,r,s)}q.a=0
C.b.P(x)
return v},
E5(){var x,w,v,u,t,s,r=this.a
if(r===0)return $.GZ()
x=new Uint8Array(r)
for(r=this.b,w=r.length,v=0,u=0;u<r.length;r.length===w||(0,B.S)(r),++u,v=s){t=r[u]
s=v+t.length
C.n.c_(x,v,s,t)}return x},
gD(d){return this.a},
gae(d){return this.a===0},
gcm(d){return this.a!==0},
P(d){this.a=0
C.b.P(this.b)}}
A.aqk.prototype={}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(B.w,[A.aqZ,A.ZH,A.aqk])})()
var y={c:B.D("ct")};(function staticFields(){$.cxA=null})();(function lazyInitializers(){var x=a.lazyFinal
x($,"cNs","GZ",()=>B.Ec(0))
x($,"cOO","cix",()=>new B.w())})()};
(a=>{a["iJ0g40TtDt15nce9RSUlBoE3z14="]=a.current})($__dart_deferred_initializers__);
((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_11",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={aph:function aph(d){this.a=0
this.b=d},Yq:function Yq(d){this.a=0
this.b=d},
ctU(){throw B.k(B.aL("Platform._environment"))},
ctr(d,e){throw B.k(B.aL("_IOService._dispatch"))},
c5e(d,e,f){var x,w
if(y.c.b(d)&&J.bXB(C.o.gb5(d))===d.length)return new A.aoC(d,e)
x=f-e
w=new Uint8Array(x)
C.o.dB(w,0,x,d,e)
return new A.aoC(w,0)},
bSk(){var x=$.ar.h(0,$.ccG())
return x==null?null:x},
ctY(){var x=$.ctV
if(x==null)A.ctU()
x.toString
return x},
aoC:function aoC(d,e){this.a=d
this.b=e}}
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[110],A)
A.aph.prototype={
t(d,e){var x,w,v,u,t,s=this,r=J.M(e),q=r.gB(e)
if(q===0)return
x=s.a+q
if(s.b.length<x)s.aFO(x)
if(y.c.b(e))C.o.bW(s.b,s.a,x,e)
else for(w=0;w<q;++w){v=s.b
u=s.a
t=r.h(e,w)
v.$flags&2&&B.au(v)
v[u+w]=t}s.a=x},
aFO(d){var x,w,v,u=d*2
if(u<1024)u=1024
else{x=u-1
x|=C.f.bl(x,1)
x|=x>>>2
x|=x>>>4
x|=x>>>8
u=((x|x>>>16)>>>0)+1}w=new Uint8Array(u)
v=this.b
C.o.bW(w,0,v.length,v)
this.b=w},
DG(){var x,w=this
if(w.a===0)return $.Gj()
x=J.dQ(C.o.gb5(w.b),w.b.byteOffset,w.a)
w.a=0
w.b=$.Gj()
return x},
DI(){var x=this
if(x.a===0)return $.Gj()
return new Uint8Array(B.ff(J.dQ(C.o.gb5(x.b),x.b.byteOffset,x.a)))},
gB(d){return this.a},
gae(d){return this.a===0},
gcg(d){return this.a!==0},
R(d){this.a=0
this.b=$.Gj()}}
A.Yq.prototype={
t(d,e){var x=y.c.b(e)?e:new Uint8Array(B.ff(e))
this.b.push(x)
this.a=this.a+x.length},
DG(){var x,w,v,u,t,s,r,q=this,p=q.a
if(p===0)return $.Gj()
x=q.b
w=x.length
if(w===1){v=x[0]
q.a=0
C.b.R(x)
return v}v=new Uint8Array(p)
for(u=0,t=0;t<x.length;x.length===w||(0,B.S)(x),++t,u=r){s=x[t]
r=u+s.length
C.o.bW(v,u,r,s)}q.a=0
C.b.R(x)
return v},
DI(){var x,w,v,u,t,s,r=this.a
if(r===0)return $.Gj()
x=new Uint8Array(r)
for(r=this.b,w=r.length,v=0,u=0;u<r.length;r.length===w||(0,B.S)(r),++u,v=s){t=r[u]
s=v+t.length
C.o.bW(x,v,s,t)}return x},
gB(d){return this.a},
gae(d){return this.a===0},
gcg(d){return this.a!==0},
R(d){this.a=0
C.b.R(this.b)}}
A.aoC.prototype={}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(B.v,[A.aph,A.Yq,A.aoC])})()
var y={c:B.B("cx")};(function staticFields(){$.ctV=null})();(function lazyInitializers(){var x=a.lazyFinal
x($,"cJz","Gj",()=>B.DE(0))
x($,"cKS","ccG",()=>new B.v())})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_11",e:"endPart",h:b})})($__dart_deferred_initializers__,"S8ts2IgehKD+/a+7gDsv2LEuG6A=");
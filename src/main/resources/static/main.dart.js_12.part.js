((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_12",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={aiQ:function aiQ(d){this.a=0
this.b=d},aie:function aie(d){this.a=0
this.b=d},
ccL(d,e,f){var x,w
if(y.c.b(d)&&d.buffer.byteLength===d.length)return new A.ai9(d,e)
x=f-e
w=new Uint8Array(x)
C.q.d_(w,0,x,d,e)
return new A.ai9(w,0)},
bMR(){var x=$.am.h(0,$.bYP())
return x==null?null:x},
ai9:function ai9(d,e){this.a=d
this.b=e}}
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[112],A)
A.aiQ.prototype={
A(d,e){var x,w,v=this,u=J.O(e),t=u.gv(e)
if(t===0)return
x=v.a+t
if(v.b.length<x)v.ayF(x)
if(y.c.b(e))C.q.bQ(v.b,v.a,x,e)
else for(w=0;w<t;++w)v.b[v.a+w]=u.h(e,w)
v.a=x},
ayF(d){var x,w,v,u=d*2
if(u<1024)u=1024
else{x=u-1
x|=C.e.bm(x,1)
x|=x>>>2
x|=x>>>4
x|=x>>>8
u=((x|x>>>16)>>>0)+1}w=new Uint8Array(u)
v=this.b
C.q.bQ(w,0,v.length,v)
this.b=w},
a2K(){var x,w,v=this,u=v.a
if(u===0)return $.DG()
x=v.b
w=B.eC(x.buffer,x.byteOffset,u)
v.a=0
v.b=$.DG()
return w},
BA(){var x,w=this.a
if(w===0)return $.DG()
x=this.b
return new Uint8Array(B.hf(B.eC(x.buffer,x.byteOffset,w)))},
gv(d){return this.a},
gag(d){return this.a===0},
gcc(d){return this.a!==0},
M(d){this.a=0
this.b=$.DG()}}
A.aie.prototype={
A(d,e){var x=y.c.b(e)?e:new Uint8Array(B.hf(e))
this.b.push(x)
this.a=this.a+x.length},
a2K(){var x,w,v,u,t,s,r,q=this,p=q.a
if(p===0)return $.DG()
x=q.b
w=x.length
if(w===1){v=x[0]
q.a=0
C.b.M(x)
return v}v=new Uint8Array(p)
for(u=0,t=0;t<x.length;x.length===w||(0,B.a2)(x),++t,u=r){s=x[t]
r=u+s.length
C.q.bQ(v,u,r,s)}q.a=0
C.b.M(x)
return v},
BA(){var x,w,v,u,t,s,r=this.a
if(r===0)return $.DG()
x=new Uint8Array(r)
for(r=this.b,w=r.length,v=0,u=0;u<r.length;r.length===w||(0,B.a2)(r),++u,v=s){t=r[u]
s=v+t.length
C.q.bQ(x,v,s,t)}return x},
gv(d){return this.a},
gag(d){return this.a===0},
gcc(d){return this.a!==0},
M(d){this.a=0
C.b.M(this.b)}}
A.ai9.prototype={}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(B.u,[A.aiQ,A.aie,A.ai9])})()
var y={c:B.B("cr")};(function lazyInitializers(){var x=a.lazyFinal
x($,"cpk","DG",()=>B.Ph(0))
x($,"cqy","bYP",()=>new B.u())})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_12",e:"endPart",h:b})})($__dart_deferred_initializers__,"vMqwtmSylOJO5666CvIM+1AXeHU=");
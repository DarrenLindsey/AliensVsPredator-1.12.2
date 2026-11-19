# Surveys the 1.12.2 creatures (stats, size, model, texture, scale, sounds) -> /tmp/survey.json.
# Run from legacy-1.12.2/src/main/java/org/avp.
import re, glob, os, json
base='.'
ents={os.path.basename(f)[:-5]:f for f in glob.glob('entities/living/**/Entity*.java', recursive=True)}
res=open('client/Resources.java').read()
models=dict(re.findall(r'MapModelTexture<(\w+)>\s+(\w+)\s*=\s*new MapModelTexture\(new \w+\(\), new Texture\([^,]+, "([^"]+)"\)\)', res) and [])
mm={}
for m in re.finditer(r'MapModelTexture<(\w+)>\s+(\w+)\s*=\s*new MapModelTexture\(new \w+\(\),\s*new Texture\([^,]+,\s*"([^"]+)"\)\)', res):
    mm[m.group(2)]=(m.group(1),m.group(3))
renders={}
reg=open('client/render/RenderRegistry.java').read() if os.path.exists('client/render/RenderRegistry.java') else ''
for f in glob.glob('client/**/*.java', recursive=True):
    t=open(f).read()
    for m in re.finditer(r'registerRenderer\((Entity\w+)\.class,\s*(\w+)\.class', t):
        renders[m.group(1)]=m.group(2)
out={}
for name,f in sorted(ents.items()):
    t=open(f).read()
    d={}
    m=re.search(r'extends (\w+)',t); d['super']=m.group(1) if m else None
    m=re.search(r'setSize\(([\d.]+)F?,\s*([\d.]+)F?\)',t); d['size']=[float(m.group(1)),float(m.group(2))] if m else None
    m=re.search(r'experienceValue\s*=\s*(\d+)',t); d['xp']=int(m.group(1)) if m else None
    for a,k in [('MAX_HEALTH','health'),('MOVEMENT_SPEED','speed'),('ATTACK_DAMAGE','damage'),('FOLLOW_RANGE','follow'),('KNOCKBACK_RESISTANCE','knockback'),('ARMOR','armor')]:
        m=re.search(a+r'\)\.setBaseValue\(([\d.]+)',t)
        if m: d[k]=float(m.group(1))
    m=re.search(r'getMatureState\(\)\s*\{\s*return (\w+)\.class',t) or re.search(r'matureState = (\w+)\.class',t); d['mature']=m.group(1) if m else None
    d['sounds']=re.findall(r'Sounds\.(\w+)\.event',t)
    r=renders.get(name)
    d['renderer']=r
    if r:
        rf=glob.glob(f'client/render/**/{r}.java', recursive=True)
        if rf:
            rt=open(rf[0]).read()
            m=re.search(r'models\(\)\.(\w+)',rt); d['res']=m.group(1) if m else None
            sc=re.findall(r'(?:OpenGL|GlStateManager)\.scale\(([\d.]+)F?,\s*([\d.]+)F?,\s*([\d.]+)F?\)',rt)
            m2=re.search(r'super\(m,\s*[^,]+,\s*([\d.]+)F?\)',rt)
            d['scale']=float(m2.group(1)) if m2 else (float(sc[0][0]) if sc else 1.0)
            if d['res'] in mm: d['model'],d['texture']=mm[d['res']]
    out[name]=d
json.dump(out,open('/tmp/survey.json','w'),indent=1)
for k,v in out.items():
    print(k, v.get('super'), v.get('size'), 'hp',v.get('health'),'dmg',v.get('damage'),'spd',v.get('speed'),'->',v.get('mature'),'|',v.get('model'),v.get('texture'),v.get('scale'), v.get('sounds')[:3])

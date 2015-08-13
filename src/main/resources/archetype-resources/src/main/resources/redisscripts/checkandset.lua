-- checkandset.lua local
local current = redis.call('GET', KEYS[1])
 if not current
   then redis.call('SET', KEYS[1], ARGV[1])
   return true
 end
 return false
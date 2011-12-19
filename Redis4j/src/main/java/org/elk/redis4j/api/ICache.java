package org.elk.redis4j.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elk.redis4j.api.cache.TypeReference;



/**
 * 当把Redis作为缓存时,实现以下基础功能<br>
 * 这里会对所有操作的key对象进行序列化存储,所以无法使用sort等redis的全部功能
 * @see link http://redis.io/commands/
 */
public interface ICache
{
	/**
	 * 从缓存服务器中清除掉全部的keys<br>
	 * [FLUSHALL]Remove all keys from all databases
	 * @see link http://redis.io/commands/flushall
	 * @return 是否成功
	 */
	public Boolean flushAllDB();

	/**
	 * 从缓存服务器中当前连接的database中清除掉全部的keys<br>
	 * [FLUSHDB]Remove all keys from the current database
	 * @see link http://redis.io/commands/flushdb
	 * @return 是否成功
	 */
	public Boolean flushCurrentDB();
	
	//hashes
	/**
	 * 删除Hash对象中的指定字段<br>
	 * [HDEL]Delete a hash field
	 * @see link http://redis.io/commands/hdel
	 * @param key 要操作的key
	 * @param field 要删除的字段
	 * @return 是否成功
	 */
	public Boolean hashesDel(String key, String field);


	/**
	 * 获取指定Hash对象中的全部字段<br>
	 * [HVALS]Get all the values in a hash
	 * @see link http://redis.io/commands/hvals
	 * @param clazz 要获取的字段的类型
	 * @param key 要操作的Hash对象的key
	 * @return 包含全部字段值的List集合
	 */
	public <T> List<T> hashesGetAllValue(Class<T> clazz, String key);

	/**
	 * 获取指定Hash对象中包含的元素个数<br>
	 * [HLEN]Get the number of fields in a hash
	 * @see link http://redis.io/commands/hlen
	 * @param key 要操作的Hash对象的key
	 * @return 指定Hash对象中包含的元素个数
	 */
	public Integer hashesLength(String key);

	/**
	 * 设置指定Hash对象的指定字段的值<br>
	 * [HSET]Set the string value of a hash field
	 * @see link http://redis.io/commands/hset
	 * @param key 要操作的Hash对象的key
	 * @param field 要操作的字段名字
	 * @param value 要存储的值
	 * @return 是否成功
	 */
	public <T> Boolean hashesSet(String key, String field, T value);


	/**
	 * 检测指定Hash对象的指定字段是否存在<br>
	 * [HEXISTS]Determine if a hash field exists
	 * @see link http://redis.io/commands/hexists
	 * @param key 要操作的Hash对象的key
	 * @param field 要操作的字段名字
	 * @return 是否包含指定的字段
	 */
	public Boolean hashesExists(String key, String field);


	/**
	 * 为指定的Hash对象的指定字段做 +increment 操作<br>
	 * [HINCRBY]Increment the integer value of a hash field by the given number
	 * @see link http://redis.io/commands/hincrby
	 * @param key  要操作的Hash对象的key
	 * @param field 要操作的字段名字
	 * @param increment 该字段要被增加的值
	 * @return field 字段被增加了 increment 后的值
	 */
	public Integer hashesIncrementByValue(String key, String field, int increment);


	/**
	 * 获取指定Hash对象的多个字段的值<br>
	 * [HMGET]Get the values of all the given hash fields
	 * @see link http://redis.io/commands/hmget
	 * @param clazz 要获取的字段的类型
	 * @param key  要操作的Hash对象的key
	 * @param fields 要获取的字段名
	 * @return 上述字段包含的值的List
	 */
	public <T> List<T> hashesMultipleFieldGet(Class<T> clazz, String key, String... fields);


	/**
	 * 设置指定Hash对象的指定字段的值,仅在该字段不存在时此操作才成功<br>
	 * [HSETNX]Set the value of a hash field, only if the field does not exist
	 * @see link http://redis.io/commands/hsetnx
	 * @param key 要操作的Hash对象的key
	 * @param field 要操作的字段名字
	 * @param value 要设置的值
	 * @return 在 field 字段不存在时返回true,否则返回false
	 */
	public <T> Boolean hashesSetNotExistField(String key, String field, T value);


	/**
	 * 获取指定Hash对象的指定字段的值<br>
	 * [HGET]Get the value of a hash field
	 * @see link http://redis.io/commands/hget
	 * @param clazz 要获取的字段的类型
	 * @param key 要操作的Hash对象的key
	 * @param field 要操作的字段名字
	 * @return filed字段的值
	 */
	public <T> T hashesGet(Class<T> clazz, String key, String field);

	/**
	 * 获取指定Hash对象的全部字段名的集合<br>
	 * [HKEYS]Get all the fields in a hash
	 * @see link http://redis.io/commands/hkeys
	 * @param key 要操作的Hash对象的key
	 * @return 此key中的全部filed的名字
	 */
	public List<String> hashesGetAllField(String key);

	/**
	 * 为指定的Hash对象同时设置多个字段的值<br>
	 * [HMSET]Set multiple hash fields to multiple values
	 * @see link http://redis.io/commands/hmset
	 * @param key 要操作的Hash对象的key
	 * @param fieldAndValue 要设置的多个字段及其值
	 * @return 是否成功
	 */
	public <T> Boolean hashesMultipleSet(String key, Map<String, T> fieldAndValue);


	/**
	 * 获取指定Hash对象的全部字段和值<br>
	 * [HGETALL]Get all the fields and values in a hash
	 * @see link http://redis.io/commands/hgetall
	 * @param clazz 要获取的字段的类型
	 * @param key  要操作的Hash对象的key
	 * @return 返回指定的Hash对象
	 */
	public <T> Map<String, T> hashesGetAll(Class<T> clazz, String key);
	
	//keys
	/**
	 * 删除指定的key<br>
	 * [DEL]Delete a key
	 * @see link http://redis.io/commands/del
	 * @param keys 要删除的key的名字
	 * @return 成功删除掉的key的数量
	 */
	public Integer del(String... keys);

	/**
	 * 在服务器中查找匹配的全部key的名字<br>
	 * [KEYS]Find all keys matching the given pattern
	 * @see link http://redis.io/commands/keys
	 * @param pattern 匹配模式,如 * 会返回全部的key
	 * @return 匹配到的key的名字集合
	 */
	public List<String> keys(String pattern);

	/**
	 * <br>
	 * [RENAME]Rename a key
	 * @param key
	 * @param newKey
	 * @return
	 */
	//public Boolean rename(String key, String newKey);

	/**
	 * 检查指定的key的数据结构类型<br>
	 * [TYPE]Determine the type stored at key
	 * @see link http://redis.io/commands/type
	 * @param key  要操作的对象的key
	 * @return 该对象在服务器中使用的数据结构的名字，如string、list、set等
	 */
	public String type(String key);

	/**
	 * 检查指定的key是否存在<br>
	 * [EXISTS]Determine if a key exists
	 * @see link http://redis.io/commands/exists
	 * @param key 要检查的key
	 * @return 是否存在
	 */
	public Boolean exists(String key);

	/**
	 * 将指定的key移动到其它数据库内<br>
	 * [MOVE]Move a key to another database
	 * @see link http://redis.io/commands/move
	 * @param key 要移动的key
	 * @param indexDB 其它数据库的ID
	 * @return 是否成功
	 */
	public Boolean move(String key, int indexDB);


	/**
	 * <br>
	 * [RENAMENX]Rename a key, only if the new key does not exist
	 * @see http://redis.io/commands/renamenx
	 * @param key
	 * @param newKey
	 * @return
	 */
	//public Boolean renameOnNotExistNewKey(String key, String newKey);

	/**
	 * 为指定的key设定一个过期时间,该 key 将在 seconds 秒后自动过期<br>
	 * [EXPIRE]Set a key's time to live in seconds
	 * @see link http://redis.io/commands/expire
	 * @param key 要操作的key
	 * @param seconds 过期时间,单位是秒
	 * @return 是否成功
	 */
	public Boolean expire(String key, int seconds);

	/**
	 * 取消指定key的过期时间<br>
	 * [PERSIST]Remove the expiration from a key
	 * @see link http://redis.io/commands/persist
	 * @param key 要操作的key
	 * @return 是否成功
	 */
	public Boolean persist(String key);

	/**
	 * 为指定的key设定一个过期时间,该 key 将在 timestamp 对应的时间到达后自动过期<br>
	 * [EXPIREAT]Set the expiration for a key as a UNIX timestamp
	 * @see link http://redis.io/commands/expireat
	 * @param key 要操作的key
	 * @param timestamp 该key的到期时间,单位是秒,不是毫秒!
	 * @return 是否成功
	 */
	public Boolean expireAsTimestamp(String key, long timestamp);

	/**
	 * 获取指定的key在过期之前剩余的时间<br>
	 * [TTL]Get the time to live for a key
	 * @see link http://redis.io/commands/ttl
	 * @param key 要操作的key
	 * @return 该key还能存在的时间,单位秒
	 */
	public Integer timeToLive(String key);
	
	//Lists
	/**
	 * 在List集合中从左侧取出一个对象并在队列中删除此对象,如果集合中没有任何对象,则该操作 会阻塞住,直到有可用对象时或到达超时<br>
	 * 由于此函数可能会被服务器阻塞住,导致当前调用线程也被阻塞住,所以在并发环境下请慎重使用,建议使用无阻塞版本{@link #listLeftPop(String)}}<br>
	 * [BLPOP]Remove and get the first element in a list, or block until one is available
	 * @see link http://redis.io/commands/blpop
	 * @param clazz 取出的对象的类型
	 * @param key 要操作的key
	 * @param timeout 如果发生阻塞,超时值
	 * @return Entry 返回从集合中取出的对象，key是List集合的名字,T是取出的对象的值
	 */
	public <T> Entry<String, T> listBlockLeftPop(Class<T> clazz, String key, int timeout);

	/**
	 * 计算指定的List集合中包含的元素个数<br>
	 * [LLEN]Get the length of a list
	 * @see link http://redis.io/commands/llen
	 * @param key 要操作的key
	 * @return 元素个数
	 */
	public Integer listLength(String key);

	/**
	 * 从List集合中删除等于value的对象<br>
	 * count > 0: Remove elements equal to value moving from head to tail.<br>
	 * count < 0: Remove elements equal to value moving from tail to head.<br>
	 * count = 0: Remove all elements equal to value.<br>
	 * [LREM]Remove elements from a list
	 * @see link http://redis.io/commands/lrem
	 * @param key 要操作的key
	 * @param count 要删除的对象在List中所处的范围
	 * @param value 要被删除的对象的值
	 * @return 被删除的对象的个数
	 */
	public <T> Integer listRemove(String key, int count, T value);

	/**
	 * 向指定的List集合中添加新的元素<br>
	 * [RPUSH]Append a value to a list
	 * @see link http://redis.io/commands/rpush
	 * @param key 要操作的key
	 * @param value 要追加的元素
	 * @return 在添加了 value 后,List集合的长度
	 */
	public <T> Integer listRightPush(String key, T value);

	/**
	 * 在List集合中从右侧取出一个对象并在队列中删除此对象,如果集合中没有任何对象,则该操作 会阻塞住,直到有可用对象时或到达超时<br>
	 * 由于此函数可能会被服务器阻塞住,导致当前调用线程也被阻塞住,所以在并发环境下请慎重使用,建议使用无阻塞版本{@link #listRightPop(String)}}<br>
	 * [BRPOP]Remove and get the last element in a list, or block until one is available
	 * @see link http://redis.io/commands/brpop
	 * @param clazz 取出的对象的类型
	 * @param key 要操作的key
	 * @param timeout 如果发生阻塞,超时值
	 * @return Entry 返回从集合中取出的对象，key是List集合的名字,T是取出的对象的值
	 */
	public <T> Entry<String, T> listBlockRightPop(Class<T> clazz, String key, int timeout);

	/**
	 * 在List集合中从左侧取出一个对象并在集合中删除此对象<br>
	 * 此函数是{@link #listBlockLeftPop(String, int)}的无阻塞版本<br>
	 * [LPOP]Remove and get the first element in a list
	 * @see link http://redis.io/commands/lpop
	 * @param clazz 取出的对象的类型
	 * @param key 要操作的key
	 * @return 取出的对象
	 */
	public <T> T listLeftPop(Class<T> clazz, String key);

	/**
	 * 在List集合中设定index位置的元素的值<br>
	 * [LSET]Set the value of an element in a list by its index
	 * @see link http://redis.io/commands/lset
	 * @param key 要操作的key
	 * @param index 要设定元素的索引位置
	 * @param value 要存储的元素
	 * @return 是否成功
	 */
	public <T> Boolean listSet(String key, int index, T value);
	
	/**
	 * 当key对应的List存在时,向该List集合追加新元素<br>
	 * [RPUSHX]Append a value to a list, only if the list exists
	 * @see link http://redis.io/commands/rpushx
	 * @param key 要操作的key
	 * @param value 要追加的元素
	 * @return 追加成功后返回该List中的全部元素个数
	 */
	public <T> Integer listRightPushOnExist(String key, T value);

	/**
	 * <br>
	 * [BRPOPLPUSH]Pop a value from a list, push it to another list and return it; or block until one is available
	 * @param source
	 * @param destination
	 * @param timeout
	 * @return
	 */
	//public <T> T listBlockRightPopLeftPush(String source, String destination, int timeout);

	/**
	 * 在List集合的左侧插入一个新元素<br>
	 * [LPUSH]Prepend a value to a list
	 * @see link http://redis.io/commands/lpush
	 * @param key 要操作的key
	 * @param value 要插入的元素
	 * @return 插入成功后返回该List中的全部元素个数
	 */
	public <T> Integer listLeftPush(String key, T value);

	/**
	 * 将一个List集合截断为指定长度<br>
	 * [LTRIM]Trim a list to the specified range
	 * @see link http://redis.io/commands/ltrim
	 * @param key 要操作的key
	 * @param start 起始位置
	 * @param stop 终止位置
	 * @return 是否成功
	 */
	public Boolean listTrim(String key, int start, int stop);

	/**
	 * 取出指定List集合中index位置的元素<br>
	 * [LINDEX]Get an element from a list by its index
	 * @see link http://redis.io/commands/lindex
	 * @param clazz 取出的元素的类型
	 * @param key 要操作的key
	 * @param index 元素的索引号
	 * @return 取出的元素
	 */
	public <T> T listIndex(Class<T> clazz, String key, int index);

	/**
	 * 当key对应的List集合存在时,向该List左侧插入新的元素<br>
	 * [LPUSHX]Prepend a value to a list, only if the list exists
	 * @see link http://redis.io/commands/lpushx
	 * @param key 要操作的key
	 * @param value 插入的值
	 * @return 插入成功后返回该List中的全部元素个数
	 */
	public <T> Integer listLeftPushOnExist(String key, T value);

	/**
	 * 在List集合中从右侧取出一个对象并在集合中删除此对象<br>
	 * 此函数是{@link #listBlockRightPop(String, int)的无阻塞版本<br>
	 * [RPOP]Remove and get the last element in a list
	 * @see link http://redis.io/commands/rpop
	 * @param clazz 取出的对象的类型
	 * @param key 要操作的key
	 * @return 取出的对象
	 */
	public <T> T listRightPop(Class<T> clazz, String key);

	/**
	 * 向指定的List集合中元素为 pivot 的前面或后面(取决于beforeOrAfter)插入新的元素<br>
	 * [LINSERT]Insert an element before or after another element in a list
	 * @see link http://redis.io/commands/linsert
	 * @param key 要操作的key
	 * @param beforeOrAfter 插入的位置
	 * @param pivot 用于判断插入位置的元素
	 * @param value 要插入的新元素
	 * @return 插入成功后返回该List中的全部元素个数,当元素 pivot 没找到时返回-1
	 */
	public <T> Integer listInsert(String key, ListPosition beforeOrAfter, T pivot, T value);

	/**
	 * 从指定的List中取出一部分元素<br>
	 * [LRANGE]Get a range of elements from a list
	 * @see link http://redis.io/commands/lrange
	 * @param clazz 取出的元素的类型
	 * @param key 要操作的key
	 * @param start 要取出的元素的起始位置
	 * @param stop 要取出的元素的终止位置
	 * @return 取出的元素的集合
	 */
	public <T> List<T> listRange(Class<T> clazz, String key, int start, int stop);

	/**
	 * <br>
	 * [RPOPLPUSH]Remove the last element in a list, append it to another list and return it
	 * @see link http://redis.io/commands/rpoplpush
	 * @param source
	 * @param destination
	 * @return
	 */
	//public <T> T listRightPopLeftPush(String source, String destination);
	
	//Sets
	/**
	 * 向set集合中添加新的元素<br>
	 * [SADD]Add a member to a set
	 * @see link http://redis.io/commands/sadd
	 * @param key 要操作的key
	 * @param member 要添加的新元素
	 * @return 是否成功
	 */
	public <T> Boolean setsAdd(String key, T member);

	/**
	 * <br>
	 * [SINTER]Intersect multiple sets
	 * @param keys
	 * @return
	 */
	//public <T> List<T> setsInter(String... keys);

	/**
	 * <br>
	 * [SMOVE]Move a member from one set to another
	 * @param source
	 * @param destination
	 * @param member
	 * @return
	 */
	//public <T> Boolean setsMove(String source, String destination, T member);

	/**
	 * <br>
	 * [SUNION]Add multiple sets
	 * @param keys
	 * @return
	 */
	//public <T> List<T> setsUnion(String... keys);

	/**
	 * 获取一个set集合中的元素数量<br>
	 * [SCARD]Get the number of members in a set
	 * @see link http://redis.io/commands/scard
	 * @param key 要操作的key
	 * @return 元素数
	 */
	public Integer setsCard(String key);

	/**
	 * <br>
	 * [SINTERSTORE]Intersect multiple sets and store the resulting set in a key
	 * @see link http://redis.io/commands/sinterstore
	 * @param destination
	 * @param keys
	 * @return
	 */
	//public Integer setsInterStore(String destination, String... keys);

	/**
	 * 从set集合中随机取出一个元素并在set中删除它<br>
	 * [SPOP]Remove and return a random member from a set
	 * @see link http://redis.io/commands/spop
	 * @param clazz 取出的元素的类型
	 * @param key 要操作的key
	 * @return set中的一个随机元素
	 */
	public <T> T setsPop(Class<T> clazz, String key);

	/**
	 * <br>
	 * [SUNIONSTORE]Add multiple sets and store the resulting set in a key
	 * @see link http://redis.io/commands/sunionstore
	 * @param destination
	 * @param keys
	 * @return
	 */
	//public Integer setsUnionStore(String destination, String... keys);

	/**
	 * <br>
	 * [SDIFF]Subtract multiple sets
	 * @see link http://redis.io/commands/sdiff
	 * @param keys
	 * @return
	 */
	//public <T> List<T> setsDiff(String... keys);

	/**
	 * 检查指定的 set 集合是否包含指定的 member 元素<br>
	 * [SISMEMBER]Determine if a given value is a member of a set
	 * @see link http://redis.io/commands/sismember
	 * @param key 要操作的key
	 * @param member 要检查的元素
	 * @return key对应的set集合是否包含 member
	 */
	public <T> Boolean setsIsMember(String key, T member);

	/**
	 * 从指定set集合中随机获取一个元素<br>
	 * [SRANDMEMBER]Get a random member from a set
	 * @see link http://redis.io/commands/srandmember
	 * @param clazz 取出的元素的类型
	 * @param key 要操作的key
	 * @return 取出的元素
	 */
	public <T> T setsRandMember(Class<T> clazz, String key);

	/**
	 * <br>
	 * [SDIFFSTORE]Subtract multiple sets and store the resulting set in a key
	 * @see link http://redis.io/commands/sdiffstore
	 * @param destination
	 * @param keys
	 * @return
	 */
	//public Integer setsDiffStore(String destination, String... keys);

	/**
	 * 返回指定set集合中的全部元素<br>
	 * [SMEMBERS]Get all the members in a set
	 * @see link http://redis.io/commands/smembers
	 * @param clazz 取出的元素的类型
	 * @param key 要操作的key
	 * @return set集合中的全部元素
	 */
	public <T> List<T> setsMembers(Class<T> clazz, String key);

	/**
	 * 从指定的set集合中删除 member<br>
	 * [SREM]Remove a member from a set
	 * @see link http://redis.io/commands/srem
	 * @param key 要操作的key
	 * @param member 要删除的元素
	 * @return 是否成功
	 */
	public <T> Boolean setsRemove(String key, T member);
	
	//SortedSets
	/**
	 * 向一个sorted set集合中添加新的元素,如果该元素已经存在则更新该元素的score<br>
	 * [ZADD]Add a member to a sorted set, or update its score if it already exists
	 * @see link http://redis.io/commands/zadd
	 * @param key 要操作的key
	 * @param score 元素 member 在 sorted set 中的排序号
	 * @param member 要添加或更新的元素
	 * @return 是否成功
	 */
	public <T> Boolean sortedSetsAdd(String key, int score, T member);

	/**
	 * <br>
	 * [ZINTERSTORE]Intersect multiple sorted sets and store the resulting sorted set in a new key
	 * @see link http://redis.io/commands/zinterstore
	 * @param destination 
	 * @param keys
	 * @return
	 */
	//public Integer sortedSetsInterStore(String destination, String... keys);

	/**
	 * 从指定的 sorted set 集合中删除 member元素<br>
	 * [ZREM]Remove a member from a sorted set
	 * @see link http://redis.io/commands/zrem
	 * @param key 要操作的key
	 * @param member 要删除的元素
	 * @return 是否成功
	 */
	public <T> Boolean sortedSetsRemove(String key, T member);

	/**
	 * 通过排序号(score)的范围,获取指定 sorted set 集合中的一部分元素(按照score从高到低排序)<br>
	 * [ZREVRANGEBYSCORE]Return a range of members in a sorted set, by score, with scores ordered from high to low
	 * @see link http://redis.io/commands/zrevrangebyscore
	 * @param clazz 取出的集合中单个对象的类型
	 * @param key 要操作的key
	 * @param max score的最大值
	 * @param min score的最小值
	 * @return 查询到的结果集
	 */
	public <T> List<T> sortedSetsRevRangeByScore(Class<T> clazz, String key, int max, int min);

	/**
	 * 获取指定sorted set集合中的元素数量<br>
	 * [ZCARD]Get the number of members in a sorted set
	 * @see link http://redis.io/commands/zcard
	 * @param key 要操作的key
	 * @return 元素数量
	 */
	public Integer sortedSetsCard(String key);

	/**
	 * 通过元素的索引号范围,获取指定 sorted set 集合中的一部分元素<br>
	 * [ZRANGE]Return a range of members in a sorted set, by index
	 * @see link http://redis.io/commands/zrange
	 * @param clazz 取出的结果集中单个对象的类型
	 * @param key 要操作的key
	 * @param start 起始索引号
	 * @param stop 终止索引号
	 * @return 查询到的结果集
	 */
	public <T> List<T> sortedSetsRange(Class<T> clazz, String key, int start, int stop);

	/**
	 * 通过元素的索引号范围,删除指定 sorted set 集合中的一部分元素<br>
	 * [ZREMRANGEBYRANK]Remove all members in a sorted set within the given indexes
	 * @see link http://redis.io/commands/zremrangebyrank
	 * @param key 要操作的key
	 * @param start 起始索引号
	 * @param stop 终止索引号
	 * @return 删除的元素数
	 */
	public Integer sortedSetsRemoveRangeByRank(String key, int start, int stop);

	/**
	 * 在指定的 sorted set 集合中，按照score从高到低的排序，计算指定的member的索引号<br>
	 * [ZREVRANK]Determine the index of a member in a sorted set, with scores ordered from high to low
	 * @see link http://redis.io/commands/zrevrank
	 * @param key 要操作的key
	 * @param member 要计算的元素
	 * @return member的索引号(按score排序后的)
	 */
	public <T> Integer sortedSetsRevRank(String key, T member);

	/**
	 * 计算指定的 sorted set 中指定score范围内的元素数量<br>
	 * [ZCOUNT]Count the members in a sorted set with scores within the given values
	 * @see link http://redis.io/commands/zcount
	 * @param key 要操作的key
	 * @param min score最小值
	 * @param max score最大值
	 * @return 统计结果
	 */
	public Integer sortedSetsCount(String key, int min, int max);

	/**
	 * 通过排序号(score)的范围,获取指定 sorted set 集合中的一部分元素(按照score从低到高排序)<br>
	 * [ZRANGEBYSCORE]Return a range of members in a sorted set, by score
	 * @see link http://redis.io/commands/zrangebyscore
	 * @param clazz 取出的结果集中单个对象的类型
	 * @param key 要操作的key
	 * @param min score的最小值
	 * @param max score的最大值
	 * @return 查询到的结果集
	 */
	public <T> List<T> sortedSetsRangeByScore(Class<T> clazz, String key, int min, int max);

	/**
	 * 从指定的 sorted set 集合中通过指定score范围删除一部分元素<br>
	 * [ZREMRANGEBYSCORE]Remove all members in a sorted set within the given scores
	 * @see link http://redis.io/commands/zremrangebyscore
	 * @param key 要操作的key
	 * @param min score最小值
	 * @param max score最大值
	 * @return 删除掉的元素数
	 */
	public Integer sortedSetsRemoveRangeByScore(String key, int min, int max);

	/**
	 * 获取指定的 sorted set 集合中指定的元素 member 的score<br>
	 * [ZSCORE]Get the score associated with the given member in a sorted set
	 * @see link http://redis.io/commands/zscore
	 * @param key 要操作的key
	 * @param member 要查询的成员
	 * @return member的score值
	 */
	public <T> Integer sortedSetsScore(String key, T member);

	/**
	 * 为指定的 sorted set 集合中的指定元素 member 的score增加 increment<br>
	 * [ZINCRBY]Increment the score of a member in a sorted set
	 * @see link http://redis.io/commands/zincrby
	 * @param key 要操作的key
	 * @param increment 要增加的数值
	 * @param member 要添加的元素
	 * @return 元素member的新的score值
	 */
	public <T> Double sortedSetsIncrementByValue(String key, int increment, T member);

	/**
	 * 计算指定的 sorted set 集合中指定的元素 member 的索引号<br>
	 * [ZRANK]Determine the index of a member in a sorted set
	 * @see link http://redis.io/commands/zrank
	 * @param key 要操作的key
	 * @param member 要计算的元素
	 * @return member的索引号
	 */
	public <T> Integer sortedSetsRank(String key, T member);

	/**
	 * 对指定的 sorted set 集合按照score从高到低进行排序,然后按照索引号范围取出一部分元素<br>
	 * [ZREVRANGE]Return a range of members in a sorted set, by index, with scores ordered from high to low
	 * @see link http://redis.io/commands/zrevrange
	 * @param clazz 取出的元素集合中单个对象的类型
	 * @param key 要操作的key
	 * @param start 起始索引号
	 * @param stop 终止索引号
	 * @return 部分元素
	 */
	public <T> List<T> sortedSetsRevRange(Class<T> clazz, String key, int start, int stop);
	
	//Strings
	/**
	 * 向一个字符串对象追加新的字符串内容，当key对应的字符串不存在时会自动创建该key<br>
	 * 注:此函数未对value进行对象序列化操作,所以不要与{@link #set(String, Object)}函数一起使用<br>
	 * [APPEND]Append a value to a key
	 * @see link http://redis.io/commands/append
	 * @param key 要操作的key
	 * @param value 追加的新字符串
	 * @return Redis服务器中该字符串的新长度
	 */
	public Integer append(String key, String value);

	/**
	 * 通过索引号范围获取一个字符串对象的子字符串<br>
	 * [GETRANGE]Get a substring of the string stored at a key
	 * @see link http://redis.io/commands/getrange
	 * @param key 要操作的key
	 * @param start 索引号起始位置
	 * @param end 索引号终止位置
	 * @return 子字符串
	 */
	public String getRange(String key, int start, int end);

	/**
	 * 同时添加多个key及其value<br>
	 * 注:此函数性能最好,比for循环多个set及批处理性能都好,在需要同时创建多个key而且value类型相同时,推荐使用此函数<br>
	 * [MSET]Set multiple keys to multiple values
	 * @see link http://redis.io/commands/mset
	 * @param keyAndValue 多个key及其value
	 * @return 是否成功
	 */
	public <T> Boolean multipleSet(Map<String, T> keyAndValue);

	/**
	 * 添加一个key,仅在这个key不存在时才会创建成功<br>
	 * [SETNX]Set the value of a key, only if the key does not exist
	 * @see link http://redis.io/commands/setnx
	 * @param key 要操作的key
	 * @param value 要添加的值
	 * @return 是否成功
	 */
	public <T> Boolean setOnNotExist(String key, T value);

	/**
	 * 为一个值为整形的key进行减一操作<br>
	 * [DECR]Decrement the integer value of a key by one
	 * @see link http://redis.io/commands/decr
	 * @param key 要操作的key
	 * @return 在key的value被减一后的值
	 */
	public Integer decrement(String key);

	/**
	 * 为一个key设定一个新的值,同时返回其原来的值<br>
	 * [GETSET]Set the string value of a key and return its old value
	 * @see link http://redis.io/commands/getset
	 * @param clazz 取出的对象的类型
	 * @param key 要操作的key
	 * @param value 新的值
	 * @return key原来的值
	 */
	public <T> T getSet(Class<T> clazz, String key, T value);

	/**
	 * 同时添加多个key,仅在这些key都不存在时才创建成功<br>
	 * [MSETNX]Set multiple keys to multiple values, only if none of the keys exist
	 * @see link http://redis.io/commands/msetnx
	 * @param keyAndValue
	 * @return
	 */
	//public Boolean multipleSetOnNotExist(Map<String, String> keyAndValue);

	/**
	 * 对一个String类型的key进行局部修改,修改位置从索引号为 offset 开始<br>
	 * [SETRANGE]Overwrite part of a string at key starting at the specified offset
	 * @see link http://redis.io/commands/setrange
	 * @param key 要操作的key
	 * @param offset 该字符串要开始修改的起始位置
	 * @param value 要替换的新字符串
	 * @return 字符串的新长度
	 */
	public Integer setRange(String key, int offset, String value);

	/**
	 * 对一个值为整形的key进行减法操作,减掉的值为decrement<br>
	 * [DECRBY]Decrement the integer value of a key by the given number
	 * @see link http://redis.io/commands/decrby
	 * @param key 要操作的key
	 * @param decrement 要减掉的值
	 * @return 减掉decrement后该key的值
	 */
	public Integer decrementByValue(String key, int decrement);

	/**
	 * 对一个值为整形的key进行加法操作,增加的值为1<br>
	 * [INCR]Increment the integer value of a key by one
	 * @see link http://redis.io/commands/incr
	 * @param key 要操作的key
	 * @return key被加一后的新值
	 */
	public Long increment(String key);

	/**
	 * 添加指定的key及其value到服务器<br>
	 * [SET]Set the string value of a key
	 * @see link http://redis.io/commands/set
	 * @param key 要操作的key
	 * @param value 值
	 * @return 是否成功
	 */
	public <T> Boolean set(String key, T value);

	/**
	 * 计算String类型的key的值在 Redis服务器中的字符长度,当key不存在时返回0<br>
	 * [STRLEN]Get the length of the value stored in a key
	 * @see link http://redis.io/commands/strlen
	 * @param key 要操作的key
	 * @return 字符串中的字符数
	 */
	public Integer strLength(String key);

	/**
	 * 获取指定的key的值<br>
	 * [GET]Get the value of a key
	 * @see link http://redis.io/commands/get
	 * @param clazz 取出的对象的类型
	 * @param key 要操作的key
	 * @return 该key的值
	 */
	public <T> T get(Class<T> clazz, String key);
	
	/**
	 * 获取指定的key的值<br>
	 * [GET]Get the value of a key
	 * @see link http://redis.io/commands/get
	 * @param clazz 取出的对象的类型,本版本函数用于获取泛型集合对象,如List等
	 * @param key 要操作的key
	 * @return 该key的值
	 */
	public <T> T get(TypeReference<T> clazz, String key);

	/**
	 * 为值为整形的key进行加法操作,增加的值为 increment<br>
	 * [INCRBY]Increment the integer value of a key by the given number
	 * @see link http://redis.io/commands/incrby
	 * @param key 要操作的key
	 * @param increment 待增加的值
	 * @return key的新值
	 */
	public Long incrementByValue(String key, int increment);

	/**
	 * 对String类型的key的值,在偏移量为 offset 处的字符进行位运算<br>
	 * [SETBIT]Sets or clears the bit at offset in the string value stored at key
	 * @see link http://redis.io/commands/setbit
	 * @param key 要操作的key
	 * @param offset 偏移量
	 * @param value 新的值
	 * @return 原来的值
	 */
	public Boolean setBit(String key, int offset, boolean value);

	/**
	 * 获取String类型的key在offset处的bit value<br>
	 * [GETBIT]Returns the bit value at offset in the string value stored at key
	 * @see link http://redis.io/commands/getbit
	 * @param key 要操作的key
	 * @param offset 偏移量
	 * @return 该偏移量处的bit值
	 */
	public Boolean getBit(String key, int offset);

	/**
	 * 同时获取多个相同类型的key的值<br>
	 * [MGET]Get the values of all the given keys
	 * @see link http://redis.io/commands/mget
	 * @param clazz 取出的结果集中单个对象的类型
	 * @param keys 多个key
	 * @return 多个key的value的集合
	 */
	public <T> List<T> multipleGet(Class<T> clazz, String... keys);

	/**
	 * 创建指定的key及其value,同时为其设定过期时间<br>
	 * [SETEX]Set the value and expiration of a key
	 * @see link http://redis.io/commands/setex
	 * @param key 要操作的key
	 * @param seconds 过期时间
	 * @param value key的值
	 * @return 是否成功
	 */
	public <T> Boolean setAndExpire(String key, int seconds, T value);
}

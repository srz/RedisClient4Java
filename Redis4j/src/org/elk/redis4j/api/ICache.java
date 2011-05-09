package org.elk.redis4j.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	 * @param key 要操作的Hash对象的key
	 * @return 包含全部字段值的List集合
	 */
	public <T> List<T> hashesGetAllValue(String key);

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
	 * @param key  要操作的Hash对象的key
	 * @param fields 要获取的字段名
	 * @return 上述字段包含的值的List
	 */
	public <T> List<T> hashesMultipleFieldGet(String key, String... fields);


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
	 * @param key 要操作的Hash对象的key
	 * @param field 要操作的字段名字
	 * @return filed字段的值
	 */
	public <T> T hashesGet(String key, String field);

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
	 * @param key  要操作的Hash对象的key
	 * @return 返回指定的Hash对象
	 */
	public <T> Map<String, T> hashesGetAll(String key);
	
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
	 * @param timestamp 该key的到期时间
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
	 * @param key 要操作的key
	 * @param timeout 如果发生阻塞,超时值
	 * @return Entry 返回从集合中取出的对象，key是List集合的名字,T是取出的对象的值
	 */
	public <T> Entry<String, T> listBlockLeftPop(String key, int timeout);

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
	 * @param key 要操作的key
	 * @param timeout 如果发生阻塞,超时值
	 * @return Entry 返回从集合中取出的对象，key是List集合的名字,T是取出的对象的值
	 */
	public <T> Entry<String, T> listBlockRightPop(String key, int timeout);

	/**
	 * 在List集合中从左侧取出一个对象并在集合中删除此对象<br>
	 * 此函数是{@link #listBlockLeftPop(String, int)}的无阻塞版本<br>
	 * [LPOP]Remove and get the first element in a list
	 * @see link http://redis.io/commands/lpop
	 * @param key 要操作的key
	 * @return 取出的对象
	 */
	public <T> T listLeftPop(String key);

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
	 * @param key 要操作的key
	 * @param index 元素的索引号
	 * @return 取出的元素
	 */
	public <T> T listIndex(String key, int index);

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
	 * @param key 要操作的key
	 * @return 取出的对象
	 */
	public <T> T listRightPop(String key);

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
	 * @param key 要操作的key
	 * @param start 要取出的元素的起始位置
	 * @param stop 要取出的元素的终止位置
	 * @return 取出的元素的集合
	 */
	public <T> List<T> listRange(String key, int start, int stop);

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
	 * @param key 要操作的key
	 * @return set中的一个随机元素
	 */
	public <T> T setsPop(String key);

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
	 * @param key 要操作的key
	 * @return 取出的元素
	 */
	public <T> T setsRandMember(String key);

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
	 * @param key 要操作的key
	 * @return set集合中的全部元素
	 */
	public <T> List<T> setsMembers(String key);

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
	 * 通过排序号的范围,获取指定 sorted set 集合中的一部分元素<br>
	 * [ZREVRANGEBYSCORE]Return a range of members in a sorted set, by score, with scores ordered from high to low
	 * @see link http://redis.io/commands/zrevrangebyscore
	 * @param key 要操作的key
	 * @param max score的最大值
	 * @param min score的最小值
	 * @return 查询到的结果集
	 */
	public <T> List<T> sortedSetsRevRangeByScore(String key, int max, int min);

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
	 * @param key 要操作的key
	 * @param start 起始索引号
	 * @param stop 终止索引号
	 * @return 查询到的结果集
	 */
	public <T> List<T> sortedSetsRange(String key, int start, int stop);

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
	 * <br>
	 * [ZCOUNT]Count the members in a sorted set with scores within the given values
	 * @see link http://redis.io/commands/zcount
	 * @param key 要操作的key
	 * @param min
	 * @param max
	 * @return
	 */
	public Integer sortedSetsCount(String key, int min, int max);

	/**
	 * <br>
	 * [ZRANGEBYSCORE]Return a range of members in a sorted set, by score
	 * @see link http://redis.io/commands/zrangebyscore
	 * @param key 要操作的key
	 * @param min 
	 * @param max
	 * @return
	 */
	public <T> List<T> sortedSetsRangeByScore(String key, int min, int max);

	/**
	 * <br>
	 * [ZREMRANGEBYSCORE]Remove all members in a sorted set within the given scores
	 * @see link http://redis.io/commands/zremrangebyscore
	 * @param key 要操作的key
	 * @param min
	 * @param max
	 * @return
	 */
	public Integer sortedSetsRemoveRangeByScore(String key, int min, int max);

	/**
	 * <br>
	 * [ZSCORE]Get the score associated with the given member in a sorted set
	 * @see link http://redis.io/commands/zscore
	 * @param key 要操作的key
	 * @param member
	 * @return
	 */
	public <T> Integer sortedSetsScore(String key, T member);

	/**
	 * <br>
	 * [ZINCRBY]Increment the score of a member in a sorted set
	 * @see link http://redis.io/commands/zincrby
	 * @param key 要操作的key
	 * @param increment
	 * @param member
	 * @return
	 */
	public <T> Double sortedSetsIncrementByValue(String key, int increment, T member);

	/**
	 * <br>
	 * [ZRANK]Determine the index of a member in a sorted set
	 * @see link http://redis.io/commands/zrank
	 * @param key 要操作的key
	 * @param member
	 * @return
	 */
	public <T> Integer sortedSetsRank(String key, T member);

	/**
	 * <br>
	 * [ZREVRANGE]Return a range of members in a sorted set, by index, with scores ordered from high to low
	 * @see link http://redis.io/commands/zrevrange
	 * @param key 要操作的key
	 * @param start
	 * @param stop
	 * @return
	 */
	public <T> List<T> sortedSetsRevRange(String key, int start, int stop);
	
	//Strings
	/**
	 * <br>
	 * [APPEND]Append a value to a key
	 * @see link http://redis.io/commands/append
	 * @param key 要操作的key
	 * @param value
	 * @return
	 */
	public Integer append(String key, String value);

	/**
	 * <br>
	 * [GETRANGE]Get a substring of the string stored at a key
	 * @see link http://redis.io/commands/getrange
	 * @param key 要操作的key
	 * @param start
	 * @param end
	 * @return
	 */
	public String getRange(String key, int start, int end);

	/**
	 * <br>
	 * [MSET]Set multiple keys to multiple values
	 * @see link http://redis.io/commands/mset
	 * @param keyAndValue
	 * @return
	 */
	public <T> Boolean multipleSet(Map<String, T> keyAndValue);

	/**
	 * <br>
	 * [SETNX]Set the value of a key, only if the key does not exist
	 * @see link http://redis.io/commands/setnx
	 * @param key 要操作的key
	 * @param value
	 * @return
	 */
	public <T> Boolean setOnNotExist(String key, T value);

	/**
	 * <br>
	 * [DECR]Decrement the integer value of a key by one
	 * @see link http://redis.io/commands/decr
	 * @param key 要操作的key
	 * @return
	 */
	public Integer decrement(String key);

	/**
	 * <br>
	 * [GETSET]Set the string value of a key and return its old value
	 * @see link http://redis.io/commands/getset
	 * @param key 要操作的key
	 * @param value
	 * @return
	 */
	public <T> T getSet(String key, T value);

	/**
	 * <br>
	 * [MSETNX]Set multiple keys to multiple values, only if none of the keys exist
	 * @see link http://redis.io/commands/msetnx
	 * @param keyAndValue
	 * @return
	 */
	public Boolean multipleSetOnNotExist(Map<String, String> keyAndValue);

	/**
	 * <br>
	 * [SETRANGE]Overwrite part of a string at key starting at the specified offset
	 * @see link http://redis.io/commands/setrange
	 * @param key 要操作的key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Integer setRange(String key, int offset, String value);

	/**
	 * <br>
	 * [DECRBY]Decrement the integer value of a key by the given number
	 * @see link http://redis.io/commands/decrby
	 * @param key 要操作的key
	 * @param decrement
	 * @return
	 */
	public Integer decrementByValue(String key, int decrement);

	/**
	 * <br>
	 * [INCR]Increment the integer value of a key by one
	 * @see link http://redis.io/commands/incr
	 * @param key 要操作的key
	 * @return
	 */
	public Long increment(String key);

	/**
	 * <br>
	 * [SET]Set the string value of a key
	 * @see link http://redis.io/commands/set
	 * @param key 要操作的key
	 * @param value
	 * @return
	 */
	public <T> Boolean set(String key, T value);

	/**
	 * <br>
	 * [STRLEN]Get the length of the value stored in a key
	 * @see link http://redis.io/commands/strlen
	 * @param key 要操作的key
	 * @return
	 */
	public Integer strLength(String key);

	/**
	 * <br>
	 * [GET]Get the value of a key
	 * @see link http://redis.io/commands/get
	 * @param key 要操作的key
	 * @return
	 */
	public <T> T get(String key);

	/**
	 * <br>
	 * [INCRBY]Increment the integer value of a key by the given number
	 * @see link http://redis.io/commands/incrby
	 * @param key 要操作的key
	 * @param increment
	 * @return
	 */
	public Long incrementByValue(String key, int increment);

	/**
	 * <br>
	 * [SETBIT]Sets or clears the bit at offset in the string value stored at key
	 * @see link http://redis.io/commands/setbit
	 * @param key 要操作的key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Boolean setBit(String key, int offset, boolean value);

	/**
	 * <br>
	 * [GETBIT]Returns the bit value at offset in the string value stored at key
	 * @see link http://redis.io/commands/getbit
	 * @param key 要操作的key
	 * @param offset
	 * @return
	 */
	public Boolean getBit(String key, int offset);

	/**
	 * <br>
	 * [MGET]Get the values of all the given keys
	 * @see link http://redis.io/commands/mget
	 * @param keys
	 * @return
	 */
	public <T> List<T> multipleGet(String... keys);

	/**
	 * <br>
	 * [SETEX]Set the value and expiration of a key
	 * @see link http://redis.io/commands/setex
	 * @param key 要操作的key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public <T> Boolean setAndExpire(String key, int seconds, T value);
}
